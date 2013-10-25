package org.marssa.web;

import org.marssa.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
@Controller
public class LoginController {

    @Autowired
    private LogService logService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
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

    @RequestMapping("/logoff")
    public String logout(ModelMap model) {
        model.addAttribute("loggedOut",true);
        logService.logUserLoggedOut(SecurityContextHolder.getContext().getAuthentication().getName());
        return "login";
    }
}