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
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Dmitriy Malakhov
 */
@Controller
@RequestMapping("*")
public class MainController {
    
    public static class Update{
        Set<Session> activeSession;
        List<SessionError> errors;
        List<Session> sessions;

        public Update(Set<Session> activeSession, List<SessionError> errors, List<Session> sessions) {
            this.activeSession = activeSession;
            this.errors = errors;
            this.sessions = sessions;
        }
        

        public Set<Session> getActiveSession() {
            return activeSession;
        }

        public List<SessionError> getErrors() {
            return errors;
        }

        public List<Session> getSessions() {
            return sessions;
        }
    }
    
    @Autowired
    private Manager manager;
    
    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String main(Model model) throws Exception {
        model.addAttribute("downloaders", manager.getDownloaders());
        return "main";
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public @ResponseBody Update update() throws Exception {
        return new Update(
            manager.getActiveSession(), 
            manager.getErrors(100, 0), 
            manager.getSessions(100, 0)
        );
    }
    
    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public @ResponseBody List<SessionError> errors(@RequestParam(name = "limit") int limit, @RequestParam(name = "offset") int offset) throws Exception {
        return manager.getErrors(limit, offset);
    }
    
    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public @ResponseBody List<Session> sessions(@RequestParam(name = "limit") int limit, @RequestParam(name = "offset") int offset) throws Exception {
        return manager.getSessions(limit, offset);
    }
    
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public @ResponseBody List<Record> records(@RequestParam(name = "limit") int limit, @RequestParam(name = "offset") int offset) throws Exception {
        return manager.getRecords(limit, offset);
    }
    
    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public @ResponseBody String run(@RequestParam(name = "id") String id) throws Exception {
        manager.submit(id);
        return "OK";
    }
}
