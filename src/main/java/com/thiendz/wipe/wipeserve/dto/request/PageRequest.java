package com.thiendz.wipe.wipeserve.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRequest {
    Integer page;
    Integer pageSize;
    String order;
    String orderBy;
}
