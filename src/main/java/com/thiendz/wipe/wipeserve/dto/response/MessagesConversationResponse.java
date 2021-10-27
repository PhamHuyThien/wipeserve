package com.thiendz.wipe.wipeserve.dto.response;

import com.thiendz.wipe.wipeserve.data.model.File;
import com.thiendz.wipe.wipeserve.data.model.Messages;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MessagesConversationResponse {
    Long id;
    String name;
    Messages newMessages;
    Messages lastMessages;
    File image;
    Long createAt;
    Long updateAt;
}
