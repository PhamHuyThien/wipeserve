package com.thiendz.wipe.wipeserve.data.repository.jpa;

import com.sun.xml.internal.ws.api.message.Attachment;
import com.thiendz.wipe.wipeserve.data.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
