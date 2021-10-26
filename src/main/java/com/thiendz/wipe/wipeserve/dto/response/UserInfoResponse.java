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
    Profile profile;
}
