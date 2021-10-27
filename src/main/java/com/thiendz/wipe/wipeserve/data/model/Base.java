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
    Long createAt;
    Long updateAt;
    Long deleteAt;
}
