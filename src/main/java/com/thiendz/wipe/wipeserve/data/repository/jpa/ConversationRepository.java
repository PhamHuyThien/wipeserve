package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.Conversation;
import com.thiendz.wipe.wipeserve.dto.response.MessagesConversationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query(value = "SELECT c.id, c.name, c.new_messages_id, p.last_messages_id, c.image_id, c.create_at, c.update_at " +
            "FROM conversation c " +
            "JOIN participants p on c.id = p.conversation_id " +
            "WHERE p.user_id = ?1 AND p.delete_at IS NULL ORDER BY c.update_at DESC ", nativeQuery = true)
    List<Object[]> findAllByConversation(Long userId);
}
