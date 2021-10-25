package com.thiendz.wipe.wipeserve.authentications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiendz.wipe.wipeserve.dto.response.Response;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointFilter implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        Response<Void> response = new Response<>(false, Message.ACCESS_DENIED);
        httpServletResponse.setHeader("Content-Type", "Application/Json; charset=UTF-8");
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(response));
    }
}
