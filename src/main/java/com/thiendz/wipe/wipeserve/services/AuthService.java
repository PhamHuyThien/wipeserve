package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.authentications.JwtTokenProvider;
import com.thiendz.wipe.wipeserve.data.model.Profile;
import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.data.repository.jpa.ProfileRepository;
import com.thiendz.wipe.wipeserve.data.repository.jpa.UserRepository;
import com.thiendz.wipe.wipeserve.dto.request.LoginRequest;
import com.thiendz.wipe.wipeserve.dto.request.RegisterRequest;
import com.thiendz.wipe.wipeserve.dto.response.LoginResponse;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import com.thiendz.wipe.wipeserve.utils.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElse(null);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UserDetails userDetails = loadUserByUsername(loginRequest.getUsername());
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_USERNAME_NOT_EXISTS);
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_PASSWORD_WRONG);
        }
        return new LoginResponse(JwtTokenProvider.generateToken(userDetails));
    }

    @Transactional
    public Void register(RegisterRequest registerRequest) {
        if (!userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_USERNAME_IS_EXISTS);
        }
        if (!userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_EMAIL_IS_EXISTS);
        }
        if (!userRepository.findByPhoneNumber(registerRequest.getPhoneNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_PHONE_NUMBER_IS_EXISTS);
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(registerRequest.getPassword())
                .status(UserStatus.DEACTIVE)
                .build();
        user = userRepository.save(user);
        Profile profile = Profile.builder()
                .address(null)
                .avatar(null)
                .cover(null)
                .firstName("Thành viên")
                .lastName("Mới")
                .user(user)
                .build();
        profileRepository.save(profile);
        return null;
    }
}
