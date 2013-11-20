package org.obp.handlers;

import org.obp.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-25
 */

@Component
public class ContextStarted implements ApplicationListener<ContextRefreshedEvent> {

    private volatile boolean initialized = false;

    @Autowired
    private LogService logService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(!initialized) {
            logService.logSystemStart();
            initialized = true;
        }
    }
}
