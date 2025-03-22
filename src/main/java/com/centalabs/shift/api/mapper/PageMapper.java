package com.centalabs.shift.api.mapper;

import com.centalabs.shift.api.dto.PageDTO;
import com.centalabs.shift.api.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    
    public <T> PageDTO<T> toDTO(Page<T> page) {
        return new PageDTO<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.isEmpty()
        );
    }

    public PageRequest toPageRequest(PageRequestDTO pageRequest) {
        Sort.Direction direction = pageRequest.getSortDirection() != null 
            ? Sort.Direction.fromString(pageRequest.getSortDirection().toUpperCase())
            : Sort.Direction.ASC;

        Sort sort = pageRequest.getSortBy() != null
            ? Sort.by(direction, pageRequest.getSortBy())
            : Sort.unsorted();

        return PageRequest.of(
            pageRequest.getPage(),
            pageRequest.getSize(),
            sort
        );
    }
} 