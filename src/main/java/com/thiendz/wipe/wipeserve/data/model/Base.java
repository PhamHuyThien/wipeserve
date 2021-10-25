package com.thiendz.wipe.wipeserve.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonFormat(pattern = "dd/MM/yyyy H:i:s")
    Date createAt;
    @JsonFormat(pattern = "dd/MM/yyyy H:i:s")
    Date updateAt;
    @JsonFormat(pattern = "dd/MM/yyyy H:i:s")
    Date deleteAt;
}
