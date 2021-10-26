package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.Profile;
import com.thiendz.wipe.wipeserve.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("SELECT p FROM Profile p " +
            "JOIN User u ON p.user = u " +
            "WHERE (u.username LIKE ?2 OR u.email LIKE ?2) AND u.id <> ?1 ")
    List<Profile> findAllByUsernameOrEmail(Long userId, String search);

    Optional<Profile> findByUser(User user);
}
