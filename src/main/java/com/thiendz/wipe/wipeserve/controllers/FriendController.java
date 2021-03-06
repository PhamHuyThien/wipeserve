package com.thiendz.wipe.wipeserve.controllers;

import com.thiendz.wipe.wipeserve.dto.request.AcceptFriendRequest;
import com.thiendz.wipe.wipeserve.dto.request.SendFriendRequest;
import com.thiendz.wipe.wipeserve.dto.request.UnfriendRequest;
import com.thiendz.wipe.wipeserve.dto.response.Response;
import com.thiendz.wipe.wipeserve.dto.response.SearchFriendResponse;
import com.thiendz.wipe.wipeserve.dto.response.SocketResponse;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
import com.thiendz.wipe.wipeserve.services.FriendsService;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/friends")
public class FriendController extends BaseController {
    @Autowired
    FriendsService friendsService;

    @GetMapping("/search-friend")
    public ResponseEntity<Response<List<SearchFriendResponse>>> searchFriend(@RequestParam String search) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.searchFriend(search, getUser())));
    }

    @MessageMapping("/{token}/list-friend-request")
    @SendTo("/messages/{token}")
    public SocketResponse<List<UserInfoResponse>> listFriendRequest() {
        return friendsService.listFriendRequest(getUser());
    }

    @MessageMapping("/{token}/list-friend")
    @SendTo("/messages/{token}")
    public SocketResponse<List<UserInfoResponse>> listFriend() {
        return friendsService.listFriend(getUser());
    }

    @PostMapping("/send-friend")
    public ResponseEntity<Response<Void>> send(@RequestBody SendFriendRequest sendFriendRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.addFriend(sendFriendRequest, getUser())));
    }

    @PostMapping("/accept-friend")
    public ResponseEntity<Response<Void>> acceptFriend(@RequestBody AcceptFriendRequest acceptFriendRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.acceptFriend(acceptFriendRequest, getUser())));
    }

    @DeleteMapping("/unfriend")
    public ResponseEntity<Response<Void>> unfriend(@RequestParam Long userId) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.unfriend(userId, getUser())));
    }

    @GetMapping("/search-friend-accept")
    public ResponseEntity<Response<List<SearchFriendResponse>>> searchFriendAccept(@RequestParam String search) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.searchFriendAccept(search, getUser())));
    }
}
