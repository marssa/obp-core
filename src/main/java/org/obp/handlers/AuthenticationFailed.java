package org.obp.handlers;

import org.obp.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
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
public class AuthenticationFailed extends ExceptionMappingAuthenticationFailureHandler {

    @Autowired
    private LogService logService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logService.logUserLogInFailed();
        super.onAuthenticationFailure(request, response, exception);
    }
}
