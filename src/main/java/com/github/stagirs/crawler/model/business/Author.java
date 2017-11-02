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

/**
 *
 * @author Dmitriy Malakhov
 */
public class Author {
    private String surname;
    private String name;
    private String status;
    private String email;
    private String organization;
    private String location;
    /**
     * @return Фамилия
     */
    public String getSurname() {
        return surname;
    }
    /**
     * @return Имя, отчество или инициалы
     */
    public String getName() {
        return name;
    }
    /**
     * @return например, студент, аспирант, канд. физ.-мат. наук
     */
    public String getStatus() {
        return status;
    }
    /**
     * @return адрес электронной почты
     */
    public String getEmail() {
        return email;
    }
    /**
     * @return организация к которой относится публикация
     */
    public String getOrganization() {
        return organization;
    }
    /**
     * @return город, страна
     */
    public String getLocation() {
        return location;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
