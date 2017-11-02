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
package com.github.stagirs.crawler.model.service;

import static org.apache.commons.lang.math.NumberUtils.toInt;

/**
 *
 * @author Dmitriy Malakhov
 */
public class Session {
    private String dateTime = "";
    private String downloaderId = "";
    private String downloaderName = "";
    private int time;
    private int errorCount;
    private int downloadCount;
    private int duplicateCount;
    private String fatalError = "";

    public String getDateTime() {
        return dateTime;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public String getDownloaderId() {
        return downloaderId;
    }

    public String getDownloaderName() {
        return downloaderName;
    }

    public int getDuplicateCount() {
        return duplicateCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getTime() {
        return time;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void incDownloadCount() {
        this.downloadCount++;
    }

    public void setDownloaderId(String downloaderId) {
        this.downloaderId = downloaderId;
    }

    public void setDownloaderName(String downloaderName) {
        this.downloaderName = downloaderName;
    }

    public void incDuplicateCount() {
        this.duplicateCount++;
    }

    public void incErrorCount() {
        this.errorCount++;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getFatalError() {
        return fatalError;
    }

    public void setFatalError(String fatalError) {
        this.fatalError = fatalError;
    }
    
    public static String serialize(Session session){
        StringBuilder sb = new StringBuilder();
        sb.append(session.dateTime).append("\t");
        sb.append(session.downloaderId).append("\t");
        sb.append(session.downloaderName).append("\t");
        sb.append(session.time).append("\t");
        sb.append(session.errorCount).append("\t");
        sb.append(session.downloadCount).append("\t");
        sb.append(session.duplicateCount).append("\t");
        sb.append(session.fatalError);
        return sb.toString();
    }
    
    public static Session parse(String line){
        Session session = new Session();
        String[] parts = line.split("\t", -1);
        int i = 0;
        session.dateTime = parts[i++];
        session.downloaderId = parts[i++];
        session.downloaderName = parts[i++];
        session.time = toInt(parts[i++]);
        session.errorCount = toInt(parts[i++]);
        session.downloadCount = toInt(parts[i++]);
        session.duplicateCount = toInt(parts[i++]);
        session.fatalError = parts[i++];
        return session;
    }
}
