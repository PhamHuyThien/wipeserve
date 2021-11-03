package com.thiendz.wipe.wipeserve.controllers;

import com.thiendz.wipe.wipeserve.dto.request.UpdateInfoRequest;
import com.thiendz.wipe.wipeserve.dto.response.Response;
import com.thiendz.wipe.wipeserve.dto.response.SocketResponse;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
import com.thiendz.wipe.wipeserve.services.UserService;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @MessageMapping("/{token}/user-info")
    @SendTo("/messages/{token}")
    public SocketResponse<UserInfoResponse> getUserInfo() {
        return userService.getUserInfo(getUser());
    }

    @PostMapping("/update-info")
    public ResponseEntity<Response<Void>> updateInfo(@RequestBody UpdateInfoRequest updateInfoRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, userService.updateInfo(updateInfoRequest, getUser())));
    }
}
