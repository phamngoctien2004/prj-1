package com.web.prj.Helpers;

import com.web.prj.dtos.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageHelper {
    public static <T> PageResponse<List<T>> toPageResponse(Page<T> page){
        return PageResponse.<List<T>>builder()
                .data(page.getContent())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
