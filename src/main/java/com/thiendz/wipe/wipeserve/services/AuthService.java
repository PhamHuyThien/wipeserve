package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.authentications.JwtTokenProvider;
import com.thiendz.wipe.wipeserve.data.model.File;
import com.thiendz.wipe.wipeserve.data.model.Profile;
import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.data.repository.jpa.*;
import com.thiendz.wipe.wipeserve.dto.request.CheckTokenRequest;
import com.thiendz.wipe.wipeserve.dto.request.LoginRequest;
import com.thiendz.wipe.wipeserve.dto.request.RegisterRequest;
import com.thiendz.wipe.wipeserve.dto.response.LoginResponse;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
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
    FileRepository fileRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    ParticipantsRepository participantsRepository;

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
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_USERNAME_IS_EXISTS);
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_EMAIL_IS_EXISTS);
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .status(UserStatus.ACTIVE)
                .build();
        user = userRepository.save(user);
        File cover = new File("https://i.pinimg.com/originals/ff/e2/a4/ffe2a43077f02a4723d207a34705513e.png", "Ảnh bìa mặc định", "png", 0);
        File avatar = new File("https://scr.vn/wp-content/uploads/2020/07/%E1%BA%A2nh-%C4%91%E1%BA%A1i-di%E1%BB%87n-Facebook-m%E1%BA%B7c-%C4%91%E1%BB%8Bnh-%C4%91%E1%BB%99c-%C4%91%C3%A1o-1024x1024.jpg", "Ảnh đại diện mặc định", "jpg", 0);
        cover = fileRepository.save(cover);
        avatar = fileRepository.save(avatar);
        Profile profile = Profile.builder()
                .address("Sao Hỏa")
                .avatar(avatar)
                .cover(cover)
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .user(user)
                .build();
        profileRepository.save(profile);
        return null;
    }

    public UserInfoResponse checkToken(CheckTokenRequest checkTokenRequest) {
        try {
            JwtTokenProvider.validateToken(checkTokenRequest.getToken());
            String username = JwtTokenProvider.getUsernameFromToken(checkTokenRequest.getToken());
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.USER_NOT_EXISTS);
            }
            UserInfoResponse userInfoResponse = userRepository.getByUserInfo(user.getId()).orElse(null);
            userInfoResponse.setTotalFriend(friendRepository.countBySenderOrReceiver(user.getId()));
            userInfoResponse.setTotalConversation(participantsRepository.countByUser(user.getId()));
            return userInfoResponse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_TOKEN_EXPIRED);
        }
    }
}
