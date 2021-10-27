package com.thiendz.wipe.wipeserve.data.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class MessageFile extends Base {
    @OneToOne
    @JoinColumn(name = "file_id")
    File file;

    @OneToOne
    @JoinColumn(name = "messages_id")
    Messages messages;
}
