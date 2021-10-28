package com.thiendz.wipe.wipeserve.utils;

import com.thiendz.wipe.wipeserve.dto.request.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {
    public static Pageable toPageable(PageRequest pageRequest) {
        pageRequest = ofDefault(pageRequest);
        return org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getPageSize(),
                Sort.Direction.valueOf(pageRequest.getOrder()),
                pageRequest.getOrderBy()
        );
    }

    public static PageRequest ofDefault(PageRequest pageRequest) {
        if (pageRequest.getPage() == null || pageRequest.getPage() < 1) {
            pageRequest.setPage(0);
        }
        if (pageRequest.getPageSize() == null || pageRequest.getPageSize() < 1) {
            pageRequest.setPageSize(10);
        }
        if (pageRequest.getOrder() == null || (!pageRequest.getOrder().equals("DESC") && !pageRequest.getOrder().equals("ASC"))) {
            pageRequest.setOrder("DESC");
        }
        if (pageRequest.getOrderBy() == null || pageRequest.getOrderBy().trim().length() == 0) {
            pageRequest.setOrderBy("id");
        }
        return pageRequest;
    }
}
