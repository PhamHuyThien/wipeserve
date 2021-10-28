package com.thiendz.wipe.wipeserve.controllers;

import com.thiendz.wipe.wipeserve.data.model.Conversation;
import com.thiendz.wipe.wipeserve.dto.request.MessagesConversationRequest;
import com.thiendz.wipe.wipeserve.dto.request.MessagesCreateConversationRequest;
import com.thiendz.wipe.wipeserve.dto.request.MessagesSendRequest;
import com.thiendz.wipe.wipeserve.dto.response.MessagesConversationResponse;
import com.thiendz.wipe.wipeserve.dto.response.MessagesResponse;
import com.thiendz.wipe.wipeserve.dto.response.Response;
import com.thiendz.wipe.wipeserve.dto.response.SocketResponse;
import com.thiendz.wipe.wipeserve.services.MessagesService;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/messages")
public class MessagesController extends BaseController {
    @Autowired
    MessagesService messagesService;

    @PostMapping("/create-conversation")
    public ResponseEntity<Response<Void>> createConversation(@RequestBody MessagesCreateConversationRequest messagesCreateConversationRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, messagesService.createConversation(messagesCreateConversationRequest, getUser())));
    }

    @GetMapping("/search-conversation")
    public ResponseEntity<Response<MessagesConversationResponse>> searchConversation(@RequestParam String search){
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, messagesService.searchConversation(search, getUser())));
    }

    @MessageMapping("/{token}/list-conversation")
    @SendTo("/messages/{token}")
    public SocketResponse<List<MessagesConversationResponse>> listConversation() {
        return messagesService.listConversation(getUser());
    }

    @MessageMapping("/{token}/list-messages")
    @SendTo("/messages/{token}")
    public SocketResponse<List<MessagesResponse>> listMessages(@Payload MessagesConversationRequest messagesConversationRequest) {
        return messagesService.listMessages(messagesConversationRequest, getUser());
    }

    @MessageMapping("/{token}/send-messages")
    @SendTo("/messages/{token}")
    public SocketResponse<MessagesResponse> sendMessages(@Payload MessagesSendRequest messagesSendRequest) {
        return messagesService.sendMessages(messagesSendRequest, getUser());
    }
}
