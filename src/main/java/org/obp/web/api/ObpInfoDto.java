package org.obp.web.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.net.URI;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-6
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class ObpInfoDto {
    UUID obpUuid;
    String obpName;
    String obpDescription;
    String buildId;
    URI uri;
    boolean hub;
    int knownRemotes;
    double apiMinLevel;
    double apiMaxLevel;
    double apiRequestedLevel;
    boolean apiRequestedLevelSupported;
}
