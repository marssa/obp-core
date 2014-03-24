/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.obp.log;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-23
 */

@Service
public class LogService {
    public static final String ORIGIN_SYSTEM = "SYSTEM";

    private static Logger logger = Logger.getLogger(LogService.class);

    @Autowired
    private LogDao logDao;

    @Transactional
    public void logSystemStart() {
        logDao.save(new LogEntry(LogEntry.Level.INFO,ORIGIN_SYSTEM,"system started"));
    }

    @Transactional
    public void logSystemStop() {
        logDao.save(new LogEntry(LogEntry.Level.INFO,ORIGIN_SYSTEM,"system stopped"));
    }

    @Transactional
    public void logUserLoggedIn(String username) {
        logDao.save(new LogEntry(LogEntry.Level.INFO,ORIGIN_SYSTEM,"user "+username+" logged in"));
    }

    @Transactional
    public void logUserLoggedOut(String username) {
        logDao.save(new LogEntry(LogEntry.Level.INFO,ORIGIN_SYSTEM,"user "+username+" logged out"));
    }

    @Transactional
    public void logUserLogInFailed() {
        logDao.save(new LogEntry(LogEntry.Level.WARN,ORIGIN_SYSTEM,"log-in attempt failed"));
    }

    @Transactional
    public List<LogEntry> selectEntries(String origin, long startTime, long endTime) {
        return logDao.selectEntries(origin, startTime, endTime);
    }
}
