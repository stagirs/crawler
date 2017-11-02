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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stagirs.crawler.model.business.Record;
import com.github.stagirs.crawler.model.service.Session;
import com.github.stagirs.crawler.model.service.SessionError;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dmitriy Malakhov
 */
@Component
public class Manager {
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private File errorsDir = new File(System.getProperty("catalina.home") + "/crawler/errors");
    private File sessionsDir = new File(System.getProperty("catalina.home") + "/crawler/sessions");
    private File recordsDir = new File(System.getProperty("catalina.home") + "/crawler/records");
    
    @PostConstruct
    public void init(){
        sessionsDir.mkdirs();
        recordsDir.mkdirs();
        errorsDir.mkdirs();
    }
    
    public String[] getSessionsPeriod(){
        return sessionsDir.list();
    }
    
    public String[] getErrorsPeriod(){
        return errorsDir.list();
    }
    
    public String[] getRecordsPeriod(){
        return recordsDir.list();
    }

    public List<Session> getSessions(String period) throws IOException {
        List<Session> sessions = new ArrayList<>();
        for(String line : FileUtils.readLines(new File(sessionsDir, period), "utf-8")){
            sessions.add(Session.parse(line));
        }
        return sessions;
    }
    
    public List<SessionError> getErrors(String period) throws IOException {
        List<SessionError> errors = new ArrayList<>();
        for(String line : FileUtils.readLines(new File(errorsDir, period), "utf-8")){
            errors.add(SessionError.parse(line));
        }
        return errors;
    }
    
    public List<Record> getRecords(String period) throws IOException {
        List<Record> records = new ArrayList<>();
        ObjectMapper om = new ObjectMapper(); 
        for(String line : FileUtils.readLines(new File(recordsDir, period), "utf-8")){
            records.add(om.readValue(line, Record.class));
        }
        return records;
    }
    
    public void addSession(Session session){
        String periodName = Utils.SDF.MONTH.format(new Date());
        try {
            FileUtils.write(new File(sessionsDir, periodName), Session.serialize(session) + "\n", "utf-8", true);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void addError(Downloader downloader, SessionError e){
        String periodName = Utils.SDF.DATE.format(new Date());
        try {
            FileUtils.write(new File(sessionsDir, periodName), SessionError.serialize(e) + "\n", "utf-8", true);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void addRecord(Record record){
        String periodName = Utils.SDF.MONTH.format(new Date());
        try {
            ObjectMapper om = new ObjectMapper(); 
            FileUtils.write(new File(recordsDir, periodName), om.writeValueAsString(record) + "\n", "utf-8", true);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void submit(Runnable task, long delay, TimeUnit timeUnit){
        executor.schedule(task, delay, timeUnit);
    }
    
    @PreDestroy
    public void destroy(){
        executor.shutdown();
    }
}
