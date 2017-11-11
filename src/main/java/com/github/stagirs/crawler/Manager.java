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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private File errorsDir = new File(System.getProperty("catalina.home") + "/work/crawler/errors");
    private File sessionsDir = new File(System.getProperty("catalina.home") + "/work/crawler/sessions");
    private File recordsDir = new File(System.getProperty("catalina.home") + "/work/crawler/records");
    private Set<Session> activeSession = new HashSet<Session>();
    private List<Downloader> downloaders = new ArrayList<>();
    
    @PostConstruct
    public void init(){
        sessionsDir.mkdirs();
        recordsDir.mkdirs();
        errorsDir.mkdirs();
    }
    
    public void submit(String id) {
        for (Downloader downloader : downloaders) {
            if(!downloader.getId().equals(id)){
                continue;
            }
            executor.submit(downloader);
        }
    }
    
    public List<Downloader> getDownloaders() {
        return downloaders;
    }
    
    public Set<Session> getActiveSession() {
        return activeSession;
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
    
    private List<String> getLines(File dir, int limit, int offset) throws IOException{
        List<String> lines = new ArrayList<>();
        File[] files = dir.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return - o1.getName().compareTo(o2.getName());
            }
        });
        for (File file : files) {
            List<String> list = FileUtils.readLines(file, "utf-8");
            for (int i = list.size() - 1; i >= 0; i--) {
                if(offset > 0){
                    offset--;
                    continue;
                }
                lines.add(list.get(i));
                if(list.size() >= limit){
                    return lines;
                }
            }
        }
        return lines;
    }

    public List<Session> getSessions(int limit, int offset) throws IOException {
        List<Session> sessions = new ArrayList<>();
        for(String line : getLines(sessionsDir, limit, offset)){
            sessions.add(Session.parse(line));
        }
        return sessions;
    }
    
    public List<SessionError> getErrors(int limit, int offset) throws IOException {
        List<SessionError> errors = new ArrayList<>();
        for(String line : getLines(errorsDir, limit, offset)){
            errors.add(SessionError.parse(line));
        }
        return errors;
    }
    
    public List<Record> getRecords(int limit, int offset) throws IOException {
        List<Record> records = new ArrayList<>();
        ObjectMapper om = new ObjectMapper(); 
        for(String line : getLines(recordsDir, limit, offset)){
            records.add(om.readValue(line, Record.class));
        }
        return records;
    }
    
    public void saveSession(Session session){
        activeSession.remove(session);
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
            FileUtils.write(new File(errorsDir, periodName), SessionError.serialize(e) + "\n", "utf-8", true);
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
