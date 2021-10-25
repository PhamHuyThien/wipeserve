package com.thiendz.wipe.wipeserve.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponse<T> extends Response<T> {
    protected long total;
    protected long totalElements;
    protected long pageSize;
    protected long pageTotal;

    public PageResponse(List<T> data, long totalElements, long pageSize) {
        this.data = (T) data;
        this.total = data.size();
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.pageTotal = totalElements / (pageSize == 0 ? totalElements : pageSize);
    }
}
