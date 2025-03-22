package com.centalabs.shift.api.mapper;

import com.centalabs.shift.api.domain.Place;
import com.centalabs.shift.api.dto.PlaceDTO;

import java.util.List;

public interface BaseMapper<DTO, Entity> {

    DTO fromEntity(Entity entity);

    Entity fromDTO(DTO entity);

    List<DTO> fromEntity(List<Entity> all);

    List<Entity> fromDTO(List<DTO> all);
}
