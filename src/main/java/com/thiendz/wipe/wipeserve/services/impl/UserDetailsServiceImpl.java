package com.thiendz.wipe.wipeserve.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
        userDetailsImpl.setUsername("thien");
        userDetailsImpl.setPassword("thiendz");
        return userDetailsImpl;
    }
}
