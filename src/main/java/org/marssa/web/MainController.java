package org.marssa.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Robert Jaremczak
 * Date: 10/2/13
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String welcomePage() {
        return "welcome";
    }
}
