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

package org.obp.web.log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.obp.log.LogEntry;
import org.obp.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-23
 */

@Controller
public class LogController {

    private static final DateTimeFormatter timestampFormatter = DateTimeFormat.forPattern("yyyy.MM.dd HH:mm:ss").withZoneUTC();

    @Autowired
    private LogService logService;

    private List<LogDto> repack(List<LogEntry> entries) {
        List<LogDto> dtos = new ArrayList<>(entries.size());
        for(LogEntry logEntry : entries) {
            LogDto dto = new LogDto();
            dto.id = logEntry.getId();
            dto.timestamp = timestampFormatter.print(logEntry.getTimestamp());
            dto.level = logEntry.getLevel().name();
            dto.origin = logEntry.getOrigin();
            dto.message = logEntry.getMessage();
            dtos.add(dto);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping("/api/log/today")
    public List<LogDto> todaySystemEntries() {
        DateTime now = DateTime.now(DateTimeZone.UTC);
        List<LogEntry> entries = logService.selectEntries(
                LogService.ORIGIN_SYSTEM,
                now.withTime(0,0,0,0).getMillis(),
                now.withTime(23,59,59,999).getMillis());

        return repack(entries);
    }

    @RequestMapping("/todaySystemLog")
    public String todaySystemLog(ModelMap model) {
        DateTime now = DateTime.now(DateTimeZone.UTC);
        List<LogEntry> entries = logService.selectEntries(
                LogService.ORIGIN_SYSTEM,
                now.withTime(0,0,0,0).getMillis(),
                now.withTime(23,59,59,999).getMillis());

        model.addAttribute("logEntries", entries);
        return "todaySystemLog";
    }
}
