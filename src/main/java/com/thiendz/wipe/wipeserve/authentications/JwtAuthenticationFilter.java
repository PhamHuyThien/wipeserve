package com.thiendz.wipe.wipeserve.authentications;

import com.thiendz.wipe.wipeserve.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AuthService authService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info(httpServletRequest.getMethod() + ": " + httpServletRequest.getRequestURI() + " - " + httpServletRequest.getRemoteAddr());
        String header = httpServletRequest.getHeader(JwtTokenProvider.HEADER);
        if (header != null && header.startsWith(JwtTokenProvider.PREFIX)) {
            final String jwtToken = header.replace(JwtTokenProvider.PREFIX, "");
            try {
                JwtTokenProvider.validateToken(jwtToken);
                String username = JwtTokenProvider.getUsernameFromToken(jwtToken);
                UserDetails userDetails = authService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
