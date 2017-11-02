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
package com.github.stagirs.crawler.model.business;

import java.util.List;

/**
 *
 * @author Dmitriy Malakhov
 */
public class Record {
    enum Type{
        /**Журнальная статья*/
        PAPER, 
        /**Доклад на конференции*/
        REPORT, 
        /**Дипломная работа*/
        DIPLOMA,
        /**Диссертация*/
        DIS,
        /**Автореферат*/
        DIS_ABSTR;
    }
    
    private String dateTime;
    private long hash;
    private String downloaderId;
    private String url;
    private Type type;
    private String title;
    private List<Author> authors;
    private String year;
    private String location;
    private String udc;
    private String doi;
    private String classificators;
    private String pages;
    private String annotation;
    private String keyWords;
    private String bibliography;
    
    public String getDateTime() {
        return dateTime;
    }

    public long getHash() {
        return hash;
    }

    public String getDownloaderId() {
        return downloaderId;
    }

    public String getUrl() {
        return url;
    }

    public Type getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getYear() {
        return year;
    }

    public String getLocation() {
        return location;
    }

    public String getUdc() {
        return udc;
    }

    public String getDoi() {
        return doi;
    }

    public String getClassificators() {
        return classificators;
    }

    public String getPages() {
        return pages;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public void setDownloaderId(String downloaderId) {
        this.downloaderId = downloaderId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUdc(String udc) {
        this.udc = udc;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setClassificators(String classificators) {
        this.classificators = classificators;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }
    

    
}
