package com.thiendz.wipe.wipeserve.dto.response;

import com.thiendz.wipe.wipeserve.data.model.File;
import com.thiendz.wipe.wipeserve.data.model.Messages;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MessagesResponse {
    UserInfoResponse user;
    Messages messages;
    List<File> attachments;
}
