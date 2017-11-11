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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dmitriy Malakhov
 */
public class Record {
    public enum Type{
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
    private Type type;
    
    private String url;
    private String title;
    private String annotation;
    private List<String> keyWords = new ArrayList<>();
    private List<String> bibliography = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    
    private Map<String, String> classificators = new HashMap<>();
    
    private String source;
    private String pages;
    private String location;
    private String year;
    private String doi;
    
    
    /**
     * @return дата обработки
     */
    public String getDateTime() {
        return dateTime;
    }
    /**
     * @return уникальный идентификатор публикации
     */
    public long getHash() {
        return hash;
    }
    /**
     * @return название класса, обработавшего публикацию
     */
    public String getDownloaderId() {
        return downloaderId;
    }
    /**
     * @return адрес по которому доступен полный текст публикации
     */
    public String getUrl() {
        return url;
    }
    /**
     * @return тип публикации
     */
    public Type getType() {
        return type;
    }
    /**
     * @return название публикации
     */
    public String getTitle() {
        return title;
    }
    /**
     * @return аннотация для публикации
     */
    public String getAnnotation() {
        return annotation;
    }
    /**
     * @return ключевые слова для публикации
     */
    public List<String> getKeyWords() {
        return keyWords;
    }
    /**
     * @return список литературы
     */
    public List<String> getBibliography() {
        return bibliography;
    }
    /**
     * @return список авторов
     */
    public List<Author> getAuthors() {
        return authors;
    }
    /**
     * @return название журнала или конференции
     */
    public String getSource() {
        return source;
    }
    /**
     * @return номер выпуска, раздел и страницы
     */
    public String getPages() {
        return pages;
    }
    /**
     * @return год выпуска
     */
    public String getYear() {
        return year;
    }
    /**
     * @return местоположение выпуска
     */
    public String getLocation() {
        return location;
    }
    /**
     * @return классификаторы
     */
    public Map<String, String> getClassificators() {
        return classificators;
    }
    /**
     * @return DOI идентификатор
     */
    public String getDoi() {
        return doi;
    }


    public void setSource(String source) {
        this.source = source;
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

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setClassificators(Map<String, String> classificators) {
        this.classificators = classificators;
    }

    

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public void setBibliography(List<String> bibliography) {
        this.bibliography = bibliography;
    }
    

    
}
