package com.thiendz.wipe.wipeserve.dto.request;

import com.thiendz.wipe.wipeserve.data.model.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessagesSendRequest {
    Long conversationId;
    String message;
    List<File> attachments;
    Long qouteId;
}
