package com.thiendz.wipe.wipeserve.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateInfoRequest {
    String firstName;
    String lastName;
    String image;
    String email;
    String address;
    String password;
}
