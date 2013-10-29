package org.marssa.web.obp;

import org.marssa.obp.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

@Controller
public class ObpController {

    @Autowired
    private Realm realm;

    @RequestMapping("/realm/details")
    public String realmDetails(ModelMap model) {
        model.addAttribute("realm", realm);
        return "realmDetails";
    }
}
