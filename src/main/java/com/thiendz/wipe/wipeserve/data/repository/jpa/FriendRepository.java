package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.Friend;
import com.thiendz.wipe.wipeserve.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT f FROM Friend f " +
            "WHERE (f.sender.id = ?1 AND f.receiver.id = ?2) OR (f.sender.id = ?2 AND f.sender.id = ?1)")
    Optional<Friend> findBySenderOrReceiver(Long userIdMe, Long userIdYou);

    List<Friend> findByReceiver(User user);
}
