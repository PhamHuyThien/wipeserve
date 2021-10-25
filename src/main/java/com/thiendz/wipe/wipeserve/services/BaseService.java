package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

public class BaseService {
    public User getUser() {
        Object objectUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (objectUser == null || !(objectUser instanceof User)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCESS_DENIED);
        }
        return (User) objectUser;
    }
}
