package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.Messages;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    @Query("SELECT mess FROM Messages mess " +
            "JOIN Conversation con ON mess.conversation = con " +
            "JOIN Participants par ON par.conversation = con " +
            "JOIN User user ON par.user = user " +
            "WHERE user.id = ?1 AND con.id = ?2 AND mess.deleteAt IS NULL ORDER BY mess.createAt DESC")
    List<Messages> findAllByUserAndConversation(Long userId, Long conversationId, Pageable pageable);
}
