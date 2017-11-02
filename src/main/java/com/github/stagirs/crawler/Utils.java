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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Dmitriy Malakhov
 */
public class Utils {
    
    enum SDF{
        DATE("yyyy-MM-dd"), 
        DATE_TIME("yyyy-MM-dd HH:mm:ss"), 
        MONTH("yyyy-MM");
        ThreadLocal<SimpleDateFormat> sdf;

        private SDF(final String pattern) {
            this.sdf = new ThreadLocal<SimpleDateFormat>(){
                @Override
                public SimpleDateFormat get() {
                    SimpleDateFormat value = super.get();
                    if(value == null){
                        value = new SimpleDateFormat(pattern);
                        super.set(value);
                    }
                    return value;
                }
            };
        }
        public ThreadLocal<SimpleDateFormat> get(){
            return sdf;
        }
        
        public String format(Date date){
            return sdf.get().format(date);
        }

        public Date parse(String date){
            try {
                return sdf.get().parse(date);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public static String toString(Throwable e){
        if(e.getCause() == null){
            return e.getMessage() + " " + StringUtils.join(e.getStackTrace(), " ");
        }else{
            return toString(e.getCause());
        }
    }
}
