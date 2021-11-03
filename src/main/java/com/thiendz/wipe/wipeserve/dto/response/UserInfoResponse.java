package com.thiendz.wipe.wipeserve.dto.response;

import com.thiendz.wipe.wipeserve.data.model.Profile;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserInfoResponse {
    Long id;
    String username;
    String email;
    Long totalFriend;
    Long totalConversation;
    Profile profile;

    public UserInfoResponse(Long id, String username, String email, Profile profile) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profile = profile;
    }
}
