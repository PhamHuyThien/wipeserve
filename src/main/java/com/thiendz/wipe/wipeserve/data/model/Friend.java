package com.thiendz.wipe.wipeserve.data.model;

import com.thiendz.wipe.wipeserve.utils.enums.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Friend extends Base {
    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;

    @Enumerated(EnumType.STRING)
    FriendStatus status;

    @ManyToOne
    @JoinColumn(name = "blocker_id")
    User blocker;
}
