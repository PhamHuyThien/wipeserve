package com.thiendz.wipe.wipeserve.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class File extends Base {
    String url;
    String name;
    String type;
    long size;
}
