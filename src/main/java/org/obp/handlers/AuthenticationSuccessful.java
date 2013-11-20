package org.obp.handlers;

import org.obp.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-25
 */

@Component
public class AuthenticationSuccessful extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private LogService logService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logService.logUserLoggedIn(authentication.getName());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
