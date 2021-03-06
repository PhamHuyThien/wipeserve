package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.Conversation;
import com.thiendz.wipe.wipeserve.data.model.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    @Query("SELECT p FROM Participants p " +
            "WHERE p.conversation.id = ?1 AND p.user.id = ?2 ")
    Optional<Participants> findByConversationAndUser(Long conversationId, Long userId);

    List<Participants> findAllByConversation(Conversation conversation);

    @Query("SELECT COUNT(p) FROM Participants p " +
            "WHERE p.user.id = ?1")
    Long countByUser(Long userId);
}
