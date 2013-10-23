package org.marssa.web.log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-23
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class LogDto {
    long id;
    String timestamp;
    String level;
    String origin;
    String message;
}
