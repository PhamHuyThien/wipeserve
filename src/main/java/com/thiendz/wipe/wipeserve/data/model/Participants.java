package com.thiendz.wipe.wipeserve.data.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Participants extends Base {
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JoinColumn(name = "last_messages_id")
    @OneToOne
    Messages lastMessages;
}
