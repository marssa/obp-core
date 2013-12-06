package org.obp.web.api;

import org.apache.commons.lang3.Range;
import org.obp.LocalObpInstance;
import org.obp.web.config.ObpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-6
 */

@Controller
@RequestMapping(InfoApi.API_PREFIX)
public class InfoApi {

    public static Range<Double> supportedApiLevels = Range.between(1.0, 1.0);

    public static final String API_PREFIX = "/api";
    public static final String API_1_0_PREFIX = "/1.0";

    @Autowired
    private LocalObpInstance localObpInstance;

    @Autowired
    private ObpConfig config;

    @ResponseBody
    @RequestMapping("/{apiLevel:\\d+\\.\\d+}/info")
    public InfoDto info(@PathVariable("apiLevel") double apiLevel) {
        InfoDto dto = new InfoDto();
        dto.apiMinLevel = supportedApiLevels.getMinimum();
        dto.apiMaxLevel = supportedApiLevels.getMaximum();
        dto.apiRequestedLevel = apiLevel;
        dto.apiRequestedLevelSupported = supportedApiLevels.contains(apiLevel);
        dto.buildId = config.getBuildId();
        dto.obpUuid = localObpInstance.getUuid();
        dto.obpName = localObpInstance.getName();
        dto.obpDescription = localObpInstance.getDescription();
        return dto;
    }

    @Secured("ROLE_ADMIN")
    @ResponseBody
    @RequestMapping(API_1_0_PREFIX+"/diagnostic")
    public DiagnosticDto diagnostic() {
        DiagnosticDto dto = new DiagnosticDto();
        dto.java = config.getJavaInfo();
        dto.os = config.getOsInfo();
        return dto;
    }

}
