package org.obp.web.api;

import org.obp.local.LocalObpInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-6
 */

@Controller
@RequestMapping(LocalObpInfo.API_PREFIX)
public class RemoteObpInfo {

    @Autowired
    private LocalObpInstance localObpInstance;

    @ResponseBody
    @RequestMapping(LocalObpInfo.API_1_0_PREFIX+"/remote")
    public List<ObpInfoDto> remote() {
        return new ArrayList<>();
    }
}
