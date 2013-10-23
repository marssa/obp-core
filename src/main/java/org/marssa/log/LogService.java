package org.marssa.log;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    @PostConstruct
    @Transactional
    public void init() {
        //logSystemStart();
    }

    @PreDestroy
    @Transactional
    public void destroy() {
        //logSystemStop();
    }

    @Transactional
    public void logSystemStart() {
        logDao.save(new LogEntry(LogEntry.Level.INFO,ORIGIN_SYSTEM,"system start"));
    }

    @Transactional
    public void logSystemStop() {
        logDao.save(new LogEntry(LogEntry.Level.INFO,ORIGIN_SYSTEM,"system stop"));
    }

    @Transactional
    public void logUserLoggerIn(String username) {
        logDao.save(new LogEntry(LogEntry.Level.INFO,ORIGIN_SYSTEM,"user "+username+" logged in"));
    }

    @Transactional
    public void logUserLogInFailed(String username) {
        logDao.save(new LogEntry(LogEntry.Level.WARN,ORIGIN_SYSTEM,"failed log-in attempt of "+username));
    }

    @Transactional
    public List<LogEntry> selectEntries(String origin, long startTime, long endTime) {
        return logDao.selectEntries(origin, startTime, endTime);
    }
}
