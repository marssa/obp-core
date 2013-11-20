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
