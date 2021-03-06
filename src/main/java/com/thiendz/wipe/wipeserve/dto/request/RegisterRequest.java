package com.thiendz.wipe.wipeserve.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequest {
    String firstName;
    String lastName;
    String username;
    String password;
    String email;
}
