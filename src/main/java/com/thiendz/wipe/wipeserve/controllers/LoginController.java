package com.thiendz.wipe.wipeserve.controllers;

import com.thiendz.wipe.wipeserve.authentications.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String login() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("");
        return JwtTokenProvider.generateToken(userDetails);
    }
}
