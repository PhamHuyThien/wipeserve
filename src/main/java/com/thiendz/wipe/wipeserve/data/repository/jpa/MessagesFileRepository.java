package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.MessageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessagesFileRepository extends JpaRepository<MessageFile, Long> {
    @Query("SELECT m FROM MessageFile m " +
            "WHERE m.messages.id = ?1")
    Optional<List<MessageFile>> findAllByMessages(Long messagesId);
}
