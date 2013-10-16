package org.marssa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
@Service
public class ObpConfig {

    @Value("${build.id}")
    private String buildId;

    public String getBuildId() {
        return buildId;
    }
}
