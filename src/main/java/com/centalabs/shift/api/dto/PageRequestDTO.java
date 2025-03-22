package com.centalabs.shift.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
} 