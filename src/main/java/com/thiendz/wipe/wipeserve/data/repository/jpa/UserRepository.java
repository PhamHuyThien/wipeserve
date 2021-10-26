package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.User;
import com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT new com.thiendz.wipe.wipeserve.dto.response.UserInfoResponse( " +
            "u.id, " +
            "u.username, " +
            "u.email, " +
            "p " +
            ") FROM User u " +
            "JOIN Profile p ON p.user.id = u.id " +
            "WHERE u.id = ?1 ")
    Optional<UserInfoResponse> getByUserInfo(Long accountId);
}
