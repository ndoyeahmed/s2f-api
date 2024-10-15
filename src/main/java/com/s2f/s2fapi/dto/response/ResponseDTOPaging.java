package com.s2f.s2fapi.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTOPaging<T> {
    private List<T> result;
    private int page;
    private long totalElements;
    private int totalPages;
}
