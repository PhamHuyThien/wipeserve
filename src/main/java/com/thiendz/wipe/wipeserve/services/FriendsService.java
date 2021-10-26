package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.data.model.Friend;
import com.thiendz.wipe.wipeserve.data.model.Profile;
import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.data.repository.jpa.FriendRepository;
import com.thiendz.wipe.wipeserve.data.repository.jpa.ProfileRepository;
import com.thiendz.wipe.wipeserve.data.repository.jpa.UserRepository;
import com.thiendz.wipe.wipeserve.dto.request.AcceptFriendRequest;
import com.thiendz.wipe.wipeserve.dto.request.SendFriendRequest;
import com.thiendz.wipe.wipeserve.dto.response.SearchFriendResponse;
import com.thiendz.wipe.wipeserve.dto.response.SocketResponse;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
import com.thiendz.wipe.wipeserve.utils.DateUtils;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import com.thiendz.wipe.wipeserve.utils.enums.FriendStatus;
import com.thiendz.wipe.wipeserve.utils.enums.SocketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendsService extends BaseService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    FriendRepository friendRepository;

    public List<SearchFriendResponse> searchFriend(String search) {
        User user = getUser();
        search = "%" + search + "%";
        List<Profile> profileList = profileRepository.findAllByUsernameOrEmail(user.getId(), search);
        List<SearchFriendResponse> userInfoResponseList = profileList.stream().map(profile -> {
            User u = profile.getUser();
            profile.setUser(null);
            SearchFriendResponse searchFriendResponse = new SearchFriendResponse();
            UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                    .id(u.getId())
                    .username(u.getUsername())
                    .email(u.getEmail())
                    .profile(profile)
                    .build();
            searchFriendResponse.setUser(userInfoResponse);
            Friend friend = friendRepository.findBySenderOrReceiver(user.getId(), u.getId()).orElse(null);
            if (friend != null) {
                searchFriendResponse.setStatus(friend.getStatus());
                searchFriendResponse.setIsSender(friend.getSender().equals(user) ? true : false);
            }
            return searchFriendResponse;
        }).collect(Collectors.toList());
        return userInfoResponseList;
    }

    @Transactional
    public Void addFriend(SendFriendRequest sendFriendRequest) {
        User user = getUser();
        Optional<Friend> friend = friendRepository.findBySenderOrReceiver(user.getId(), sendFriendRequest.getUserId());
        if (friend.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.FRIEND_IS_EXISTS);
        }
        Friend friend3 = new Friend(user, userRepository.findById(sendFriendRequest.getUserId()).orElse(null), FriendStatus.SEENDING, null);
        friend3.setCreateAt(DateUtils.currentDate());
        friendRepository.save(friend3);
        return null;
    }

    public SocketResponse<List<UserInfoResponse>> listFriendRequest() {
        User user = getUser();
        List<Friend> friendList = friendRepository.findByReceiver(user);
        List<UserInfoResponse> userInfoResponseList = friendList.stream().map(friend -> {
            User u = friend.getSender();
            return UserInfoResponse.builder()
                    .id(u.getId())
                    .username(u.getUsername())
                    .email(u.getEmail())
                    .profile(profileRepository.findByUser(user).orElse(null))
                    .build();
        }).collect(Collectors.toList());
        return new SocketResponse<>(true, Message.SUCCESS, userInfoResponseList, SocketType.LIST_FRIEND_REQUEST);
    }

    public Void acceptFriend(AcceptFriendRequest acceptFriendRequest) {
        User user = getUser();
        User u = userRepository.findById(acceptFriendRequest.getUserId()).orElse(null);
        if(u == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.USER_NOT_EXISTS);
        }
        return null;
    }
}