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
