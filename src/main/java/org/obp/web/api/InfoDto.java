package org.obp.web.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-6
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class InfoDto {
    double apiMinLevel;
    double apiMaxLevel;
    double apiRequestedLevel;
    boolean apiRequestedLevelSupported;
    String buildId;
    UUID obpUuid;
    String obpName;
    String obpDescription;
}
