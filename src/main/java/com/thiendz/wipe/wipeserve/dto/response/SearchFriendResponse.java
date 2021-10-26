package com.thiendz.wipe.wipeserve.dto.response;

import com.thiendz.wipe.wipeserve.utils.enums.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchFriendResponse {
    FriendStatus status;
    Boolean isSender;
    UserInfoResponse user;
}
