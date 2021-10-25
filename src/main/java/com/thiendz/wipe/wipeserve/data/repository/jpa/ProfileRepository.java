package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.thiendz.wipe.wipeserve.data.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
