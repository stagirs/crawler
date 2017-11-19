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

import com.github.stagirs.common.HashUtils;
import com.github.stagirs.crawler.model.business.Record;
import com.github.stagirs.crawler.model.service.Session;
import com.github.stagirs.crawler.model.service.SessionError;
import gnu.trove.set.hash.TLongHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import static org.apache.commons.lang.math.NumberUtils.toLong;

/**
 *
 * @author Dmitriy Malakhov
 */
public abstract class Downloader implements Runnable{
    public static Set<Class<Downloader>> DOWNLOADERS = new HashSet<>();
    @Autowired
    protected Confiruration conf;
    @Autowired
    private Manager manager;
    private File recordIdsFile = new File(System.getProperty("catalina.home") + "/work/crawler/recordIds/" + getId());
    private File releaseIdsFile = new File(System.getProperty("catalina.home") + "/work/crawler/releaseIds/" + getId());
    private Set<String> recordIds = new HashSet<String>();
    private Set<String> releaseIds = new HashSet<String>();
    
    protected abstract void process(Session session) throws Exception;
    public abstract String getName();
    public abstract String getUrl();
    
    public String getId(){
        return this.getClass().getSimpleName();
    }
    
    @PostConstruct
    public void init(){
        recordIdsFile.getParentFile().mkdirs();
        releaseIdsFile.getParentFile().mkdirs();
        try {
            if(recordIdsFile.exists()){
                recordIds.addAll(FileUtils.readLines(recordIdsFile, "utf-8"));
            }
            if(releaseIdsFile.exists()){
                releaseIds.addAll(FileUtils.readLines(releaseIdsFile, "utf-8"));
            }
            manager.getDownloaders().add(this);
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
        manager.getActiveSession().add(session);
        long time = System.currentTimeMillis();
        try{
            process(session);
        }catch(Exception e){
            error(session, e);
            session.setFatalError(e.getMessage());
        }
        session.setTime((int) (System.currentTimeMillis() - time));
        conf.set("last.time." + getId(), Utils.SDF.DATE_TIME.format(new Date()));
        manager.saveSession(session);
    }
    
    protected void error(Session session, Exception e){
        manager.addError(this, new SessionError(Utils.SDF.DATE_TIME.format(new Date()), getId(), e));
        session.incErrorCount();
    }
    
    protected boolean isNewRelease(String releaseId){
        return !releaseIds.contains(releaseId);
    }
    
    protected void save(String releaseId){
        try {
            FileUtils.write(releaseIdsFile, releaseId + "\n", "utf-8", true);
            releaseIds.add(releaseId);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected void save(Session session, Record record){
        record.setDownloaderId(getClass().getSimpleName());
        record.setDateTime(Utils.SDF.DATE_TIME.format(new Date()));
        record.setHash(HashUtils.hash(record.getUrl() + "\t" + record.getTitle()));
        if(recordIds.contains(record.getHash())){
            session.incDuplicateCount();
            return;
        }else{
            session.incDownloadCount();
        }
        manager.addRecord(record);
        try {
            FileUtils.write(recordIdsFile, record.getUrl() + "\n", "utf-8", true);
            recordIds.add(record.getUrl());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
