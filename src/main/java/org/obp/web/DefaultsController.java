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

package org.obp.web;

import org.obp.DefaultDataInstrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Robert Jaremczak
 * Date: 2014-5-21
 */

@Controller
public class DefaultsController {

    @Autowired
    private DefaultDataInstrument defaultDataInstrument;

    @RequestMapping("/simple/defaults")
    public String manifest(ModelMap model) {
        model.addAttribute("readouts", defaultDataInstrument.getReadouts());
        return "simple/defaults";
    }

    @RequestMapping("/secure/defaults/update")
    public ResponseEntity updateDefaultValue(@RequestParam String name, @RequestParam String value) {
        try {
            defaultDataInstrument.updateReadout(name, new Double(value));
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
