package com.ubi.userservice.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {
    T listOfResults;
    Integer totalPages;
    Long totalElements;
}
