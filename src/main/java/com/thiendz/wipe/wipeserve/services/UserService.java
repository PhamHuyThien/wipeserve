package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.data.repository.jpa.UserRepository;
import com.thiendz.wipe.wipeserve.dto.response.SocketResponse;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import com.thiendz.wipe.wipeserve.utils.enums.SocketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public SocketResponse<UserInfoResponse> getUserInfo(User user) {
        SocketResponse<UserInfoResponse> userInfoResponseSocketResponse = new SocketResponse<>(Message.SUCCESS, SocketType.USER_INFO);
        UserInfoResponse userInfoResponse = userRepository.getByUserInfo(user.getId()).orElse(null);
        if (userInfoResponse == null) {
            userInfoResponseSocketResponse.setMessage(Message.USER_NOT_EXISTS);
            return userInfoResponseSocketResponse;
        }
        userInfoResponse.getProfile().setUser(null);
        userInfoResponseSocketResponse.setStatus(true);
        userInfoResponseSocketResponse.setData(userInfoResponse);
        return userInfoResponseSocketResponse;
    }
}
