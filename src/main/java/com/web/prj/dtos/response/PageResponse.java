package com.web.prj.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T>{
    private List<T> data;
    private int currentPage;
    private int totalPages;
    private long totalElement;
    private boolean lastPage;
}
