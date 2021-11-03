package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.data.model.Profile;
import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.data.repository.jpa.*;
import com.thiendz.wipe.wipeserve.dto.request.UpdateInfoRequest;
import com.thiendz.wipe.wipeserve.dto.response.SocketResponse;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import com.thiendz.wipe.wipeserve.utils.enums.SocketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    ParticipantsRepository participantsRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public SocketResponse<UserInfoResponse> getUserInfo(User user) {
        SocketResponse<UserInfoResponse> userInfoResponseSocketResponse = new SocketResponse<>(Message.SUCCESS, SocketType.USER_INFO);
        UserInfoResponse userInfoResponse = userRepository.getByUserInfo(user.getId()).orElse(null);
        if (userInfoResponse == null) {
            userInfoResponseSocketResponse.setMessage(Message.USER_NOT_EXISTS);
            return userInfoResponseSocketResponse;
        }
        userInfoResponse.getProfile().setUser(null);
        userInfoResponse.setTotalFriend(friendRepository.countBySenderOrReceiver(user.getId()));
        userInfoResponse.setTotalConversation(participantsRepository.countByUser(user.getId()));
        userInfoResponseSocketResponse.setStatus(true);
        userInfoResponseSocketResponse.setData(userInfoResponse);
        return userInfoResponseSocketResponse;
    }

    @Transactional
    public Void updateInfo(UpdateInfoRequest updateInfoRequest, User user) {
        Profile profile = profileRepository.findByUser(user).orElse(null);
        assert profile != null;
        if (updateInfoRequest.getFirstName() == null || updateInfoRequest.getFirstName().trim().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.USER_FIRSTNAME_NOT_EMPTY);
        }
        if (updateInfoRequest.getLastName() == null || updateInfoRequest.getLastName().trim().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.USER_LASTNAME_NOT_EMPTY);
        }
        if (updateInfoRequest.getEmail() == null || updateInfoRequest.getEmail().trim().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.USER_EMAIL_NOT_EMPTY);
        }
        if (updateInfoRequest.getAddress() != null && !updateInfoRequest.getAddress().trim().equals("")) {
            profile.setAddress(updateInfoRequest.getAddress());
        }
        if (updateInfoRequest.getPassword() != null && updateInfoRequest.getPassword().trim().equals("")) {
            user.setPassword(passwordEncoder.encode(updateInfoRequest.getPassword()));
        }
        profile.setFirstName(updateInfoRequest.getFirstName());
        profile.setLastName(updateInfoRequest.getLastName());
        user.setEmail(updateInfoRequest.getEmail());
        userRepository.save(user);
        profileRepository.save(profile);
        return null;
    }
}
