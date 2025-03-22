package com.centalabs.shift.api.mapper;

import com.centalabs.shift.api.domain.Place;
import com.centalabs.shift.api.dto.PlaceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceMapper extends BaseMapper<PlaceDTO, Place> {
}
