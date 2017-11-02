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

import com.github.stagirs.crawler.Utils;
import java.util.List;
import static org.apache.commons.lang.math.NumberUtils.toInt;

/**
 *
 * @author Dmitriy Malakhov
 */
public class SessionError {
    private String dateTime;
    private String downloaderId;
    private String exeption;

    private SessionError() {
    }
    
    public SessionError(String dateTime, String downloaderId, Throwable e) {
        this.dateTime = dateTime;
        this.downloaderId = downloaderId;
        this.exeption = Utils.toString(e);
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDownloaderId() {
        return downloaderId;
    }

    public String getExeption() {
        return exeption;
    }

    
    
    public static String serialize(SessionError error){
        StringBuilder sb = new StringBuilder();
        sb.append(error.dateTime).append("\t");
        sb.append(error.downloaderId).append("\t");
        sb.append(error.exeption);
        return sb.toString();
    }
    
    public static SessionError parse(String line){
        SessionError error = new SessionError();
        String[] parts = line.split("\t", -1);
        int i = 0;
        error.dateTime = parts[i++];
        error.downloaderId = parts[i++];
        error.exeption = parts[i++];
        return error;
    }
    
}
