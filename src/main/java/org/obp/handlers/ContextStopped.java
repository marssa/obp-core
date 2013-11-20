package org.obp.handlers;

import org.obp.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-25
 */

@Component
public class ContextStopped implements ApplicationListener<ContextClosedEvent> {

    private volatile boolean stopped = false;
    @Autowired
    private LogService logService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if(!stopped) {
            logService.logSystemStop();
            stopped = true;
        }
    }
}
