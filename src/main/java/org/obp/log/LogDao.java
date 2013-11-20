package org.obp.log;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-23
 */

@Repository
public class LogDao {
    private static Logger logger = Logger.getLogger(LogDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void save(LogEntry logEntry) {
        sessionFactory.getCurrentSession().save(logEntry);
        logger.debug("logged: "+logEntry);
    }

    @SuppressWarnings("unchecked")
    public List<LogEntry> selectEntries(String origin, long startTime, long endTime) {
        return sessionFactory.getCurrentSession().createCriteria(LogEntry.class)
                .add(Restrictions.eq("origin",origin))
                .add(Restrictions.between("timestamp",startTime, endTime))
                .list();
    }
}
