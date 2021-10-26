package com.thiendz.wipe.wipeserve.controllers;

import com.thiendz.wipe.wipeserve.dto.request.AcceptFriendRequest;
import com.thiendz.wipe.wipeserve.dto.request.SendFriendRequest;
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
public class FriendController {
    @Autowired
    FriendsService friendsService;

    @GetMapping("/search-friend")
    public ResponseEntity<Response<List<SearchFriendResponse>>> searchFriend(@RequestParam String search) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.searchFriend(search)));
    }

    @MessageMapping("/{token}/list-friend-request")
    @SendTo("/messages/{token}")
    public SocketResponse<List<UserInfoResponse>> listFriendRequest() {
        return friendsService.listFriendRequest();
    }

    @PostMapping("/send-friend")
    public ResponseEntity<Response<Void>> send(@RequestBody SendFriendRequest sendFriendRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.addFriend(sendFriendRequest)));
    }

    @PostMapping("/accept-friend")
    public ResponseEntity<Response<Void>> acceptFriend(@RequestBody AcceptFriendRequest acceptFriendRequest){
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, friendsService.acceptFriend(acceptFriendRequest)));
    }

}
