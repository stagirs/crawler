/*
 * Copyright 2017 Dmitriy Malakhov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stagirs.crawler;

import com.github.stagirs.crawler.model.business.Record;
import com.github.stagirs.crawler.model.service.Session;
import com.github.stagirs.crawler.model.service.SessionError;
import gnu.trove.set.hash.TLongHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import static org.apache.commons.lang.math.NumberUtils.toLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dmitriy Malakhov
 */
@Component
public abstract class Downloader implements Runnable{
    @Autowired
    private Confiruration conf;
    @Autowired
    private Manager manager;
    private File recordIdsFile = new File(System.getProperty("catalina.home") + "/crawler/recordIds/" + getId());
    private TLongHashSet recordIds = new TLongHashSet();
    
    public abstract void process(Session session);
    public abstract String getName();
    
    public int getDaysInterval(){
        return 7;
    }
    
    public String getId(){
        return this.getClass().getSimpleName();
    }
    
    @PostConstruct
    public void init(){
        try {
            for(String line : FileUtils.readLines(recordIdsFile, "utf-8")){
                recordIds.add(toLong(line));
            }
            Date lastDate = Utils.SDF.DATE_TIME.parse(conf.get("last.time." + getId()));
            manager.submit(this, (System.currentTimeMillis() - lastDate.getTime()) / 1000L * 3600 * 24, TimeUnit.DAYS);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        Session session = new Session();
        session.setDateTime(Utils.SDF.DATE_TIME.format(new Date()));
        session.setDownloaderId(getId());
        session.setDownloaderName(getName());
        long time = System.currentTimeMillis();
        try{
            process(session);
        }catch(Exception e){
            error(session, e);
            session.setFatalError(e.getMessage());
        }
        session.setTime((int) (System.currentTimeMillis() - time));
        conf.set("last.time." + getId(), Utils.SDF.DATE_TIME.format(new Date()));
        manager.submit(this, getDaysInterval(), TimeUnit.DAYS);
        manager.addSession(session);
    }
    
    protected void error(Session session, Exception e){
        manager.addError(this, new SessionError(Utils.SDF.DATE_TIME.format(new Date()), getId(), e));
        session.incErrorCount();
    }
    
    protected void save(Session session, Record record){
        if(recordIds.contains(record.getHash())){
            session.incDuplicateCount();
            return;
        }else{
            session.incDownloadCount();
        }
        manager.addRecord(record);
        try {
            FileUtils.write(recordIdsFile, record.getDateTime() + "\t" + record.getHash() + "\n", "utf-8", true);
            recordIds.add(record.getHash());
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
