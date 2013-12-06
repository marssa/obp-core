package org.obp.web.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-6
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
class DiagnosticDto {
    Object java;
    Object os;
}
