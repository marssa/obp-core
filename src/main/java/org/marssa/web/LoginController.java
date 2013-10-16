package org.marssa.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/loginFailed")
    public String loginFailed(ModelMap model) {
        model.addAttribute("failed",true);
        return "login";
    }

    @RequestMapping("/loginSucceeded")
    public String loginSucceeded() {
        return "welcome";
    }

    @RequestMapping("/logout")
    public String logout(ModelMap model) {
        model.addAttribute("loggedOut",true);
        return "login";
    }
}