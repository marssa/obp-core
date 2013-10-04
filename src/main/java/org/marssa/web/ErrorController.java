package org.marssa.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Robert Jaremczak
 * Date: 10/2/13
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/notFound")
    public String notFound() {
        return "notFound";
    }

    @RequestMapping("/internalError")
    public String internalError() {
        return "internalError";
    }
}
