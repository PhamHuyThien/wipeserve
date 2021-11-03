package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.Friend;
import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.utils.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT f FROM Friend f " +
            "WHERE (f.sender.id = ?1 AND f.receiver.id = ?2) OR (f.sender.id = ?2 AND f.receiver.id = ?1)")
    Optional<Friend> findBySenderOrReceiver(Long userIdMe, Long userIdYou);

    @Query("SELECT f FROM Friend f " +
            "WHERE (f.sender.id = ?1 OR f.receiver.id = ?1) AND f.status = 'FRIEND'")
    List<Friend> findBySenderOrReceiverAndStatus(Long userIdMe);

    List<Friend> findByReceiverAndStatus(User user, FriendStatus status);

    @Query("SELECT f FROM Friend f " +
            "WHERE f.sender.id = ?2 AND f.receiver.id = ?1 ")
    Optional<Friend> findBySenderEqualsAndReceiverEquals(Long userId, Long userYouId);

    @Query("SELECT count(f) FROM Friend f " +
            "WHERE (f.sender.id = ?1 OR f.receiver = ?1) AND f.status='FRIEND'")
    Long countBySenderOrReceiver(Long userId);
}
