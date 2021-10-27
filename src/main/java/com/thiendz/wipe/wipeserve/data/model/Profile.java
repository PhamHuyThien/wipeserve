package com.thiendz.wipe.wipeserve.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Profile extends Base {
    String firstName;
    String lastName;
    String address;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", unique = true)
    User user;

    @OneToOne
    @JoinColumn(name = "cover_id")
    File cover;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    File avatar;
}
