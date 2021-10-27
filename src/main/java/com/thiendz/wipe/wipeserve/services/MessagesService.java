package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.data.model.*;
import com.thiendz.wipe.wipeserve.data.repository.jpa.*;
import com.thiendz.wipe.wipeserve.dto.request.MessagesConversationRequest;
import com.thiendz.wipe.wipeserve.dto.request.MessagesCreateConversationRequest;
import com.thiendz.wipe.wipeserve.dto.response.MessagesConversationResponse;
import com.thiendz.wipe.wipeserve.dto.response.MessagesResponse;
import com.thiendz.wipe.wipeserve.dto.response.SocketResponse;
import com.thiendz.wipe.wipeserve.utils.DateUtils;
import com.thiendz.wipe.wipeserve.utils.DestinationUtils;
import com.thiendz.wipe.wipeserve.utils.NumberUtils;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import com.thiendz.wipe.wipeserve.utils.enums.SocketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessagesService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    ParticipantsRepository participantsRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    MessagesRepository messagesRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    MessagesFileRepository messagesFileRepository;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public Void createConversation(MessagesCreateConversationRequest messagesCreateConversationRequest, User user) {
        User u = userRepository.findById(messagesCreateConversationRequest.getUserId()).orElse(null);
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.USER_NOT_EXISTS);
        }
        if (messagesCreateConversationRequest.getConversationName().trim().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.MESSAGES_CONVERSATION_NAME_NOT_EMPTY);
        }
        Long curentDate = DateUtils.currentLongTime();
        File image = new File("https://photo-cms-kienthuc.zadn.vn/zoom/800/uploaded/tongbao/2019_09_08/buc-anh-hiem-hoi-van-toan-va-ban-gai-xinh-dep-xuat-hien-cung-mot-khung-hinh-hinh-3.jpg", "Ảnh nhóm mặc định", "jpg", 0);
        image = fileRepository.save(image);
        Conversation conversation = Conversation.builder()
                .createBy(user)
                .newMessages(null)
                .name(messagesCreateConversationRequest.getConversationName())
                .image(image)
                .build();
        conversation.setCreateAt(curentDate);
        conversation.setUpdateAt(curentDate);
        conversation = conversationRepository.save(conversation);
        Participants participants = Participants.builder()
                .lastMessages(null)
                .conversation(conversation)
                .user(user)
                .build();
        participants.setCreateAt(curentDate);
        participants.setUpdateAt(curentDate);
        participantsRepository.save(participants);
        participants = Participants.builder()
                .lastMessages(null)
                .conversation(conversation)
                .user(u)
                .build();
        participants.setCreateAt(curentDate);
        participants.setUpdateAt(curentDate);
        participantsRepository.save(participants);
        simpMessagingTemplate.convertAndSend(DestinationUtils.getDestinationOfConvertAndSend(u), listConversation(u));
        return null;
    }

    public SocketResponse<List<MessagesConversationResponse>> listConversation(User user) {
        SocketResponse<List<MessagesConversationResponse>> listSocketResponse = new SocketResponse<>(Message.SUCCESS, SocketType.LIST_CONVERSATION);
        List<Object[]> objectsList = conversationRepository.findAllByConversation(user.getId());
        List<MessagesConversationResponse> messagesConversationResponseList = objectsList.stream().map(objects ->
                MessagesConversationResponse.builder()
                        .id(NumberUtils.parseLong(objects[0]))
                        .name(objects[1].toString())
                        .newMessages(messagesRepository.findById(NumberUtils.parseLong(objects[2], -1L)).orElse(null))
                        .lastMessages(messagesRepository.findById(NumberUtils.parseLong(objects[3], -1L)).orElse(null))
                        .image(fileRepository.findById(NumberUtils.parseLong(objects[4], -1L)).orElse(null))
                        .createAt(NumberUtils.parseLong(objects[5]))
                        .updateAt(NumberUtils.parseLong(objects[6]))
                        .build()
        ).collect(Collectors.toList());
        listSocketResponse.setData(messagesConversationResponseList);
        listSocketResponse.setStatus(true);
        return listSocketResponse;
    }

    @Transactional
    public SocketResponse<List<MessagesResponse>> listMessages(MessagesConversationRequest messagesConversationRequest, User user) {
        SocketResponse<List<MessagesResponse>> listSocketResponse = new SocketResponse<>(true, Message.SUCCESS, SocketType.LIST_MESSAGES);
        if (!conversationRepository.findById(messagesConversationRequest.getConversationId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.MESSAGES_CONVERSATION_NOT_EXISTS);
        }
        List<Messages> messagesList = messagesRepository.findAllByUserAndConversation(user.getId(), messagesConversationRequest.getConversationId());
        List<MessagesResponse> messagesResponseList = messagesList.stream().map(messages -> {
            MessagesResponse messagesResponse = new MessagesResponse();
            messagesResponse.setMessages(messages);
            messagesResponse.setUserInfoResponse(userRepository.getByUserInfo(messages.getUser().getId()).orElse(null));
            messagesResponse.setAttachments(messagesFileRepository.findAllByMessages(messages.getId()).orElse(new ArrayList<>()).stream().map(messageFile -> messageFile.getFile()).collect(Collectors.toList()));
            return messagesResponse;
        }).collect(Collectors.toList());
        listSocketResponse.setData(messagesResponseList);
        Participants participants = participantsRepository.findByConversationAndUser(messagesConversationRequest.getConversationId(), user.getId()).orElse(null);
        if (participants != null) {
        }
        return listSocketResponse;
    }
}
