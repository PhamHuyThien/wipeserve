package com.thiendz.wipe.wipeserve.controllers;

import com.thiendz.wipe.wipeserve.dto.request.CheckTokenRequest;
import com.thiendz.wipe.wipeserve.dto.request.LoginRequest;
import com.thiendz.wipe.wipeserve.dto.request.RegisterRequest;
import com.thiendz.wipe.wipeserve.dto.response.LoginResponse;
import com.thiendz.wipe.wipeserve.dto.response.Response;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
import com.thiendz.wipe.wipeserve.services.AuthService;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, authService.login(loginRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Void>> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, authService.register(registerRequest)));
    }

    @PostMapping("/check-token")
    public ResponseEntity<Response<UserInfoResponse>> checkToken(@RequestBody CheckTokenRequest checkTokenRequest){
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, authService.checkToken(checkTokenRequest)));
    }
}
