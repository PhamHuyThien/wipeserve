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
public class Conversation extends Base {

    String name;

    @OneToOne
    @JoinColumn(name = "image_id")
    File image;

    @ManyToOne
    @JoinColumn(name = "create_by")
    User createBy;

    @OneToOne
    @JoinColumn(name = "new_messages_id")
    Messages newMessages;
}
