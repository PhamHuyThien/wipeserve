package com.thiendz.wipe.wipeserve.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Messages extends Base {
    String message;
    int heart;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "quote_id")
    Messages quote;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "conversation_id")
    Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
