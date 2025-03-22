package com.centalabs.shift.api.service;

import com.centalabs.shift.api.auth.AuthUserToken;
import com.centalabs.shift.api.domain.Place;
import com.centalabs.shift.api.dto.PlaceDTO;
import com.centalabs.shift.api.mapper.PlaceMapper;
import com.centalabs.shift.api.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final PlaceMapper placeMapper;

    @Transactional
    public PlaceDTO createPlace(PlaceDTO placeDTO, AuthUserToken authUserToken) {
        Place place = placeMapper.fromDTO(placeDTO);
        place.setOwner(authUserToken.getUser());
        return placeMapper.fromEntity(placeRepository.save(place));
    }

    @Transactional(readOnly = true)
    public PlaceDTO getPlaceById(UUID id) {
        Place place = placeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return placeMapper.fromEntity(place);
    }

    @Transactional(readOnly = true)
    public List<PlaceDTO> getAllPlaces() {
        return placeMapper.fromEntity(placeRepository.findAll());
    }

    @Transactional
    public void deletePlace(UUID id) {
        if (!placeRepository.existsById(id)) {
            throw new RuntimeException("Place not found");
        }
        placeRepository.deleteById(id);
    }

    @Transactional
    public PlaceDTO updatePlace(UUID id, Place updatedPlace) {
        Place existingPlace = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found"));
        existingPlace.setName(updatedPlace.getName());
        existingPlace.setShiftStartTime(updatedPlace.getShiftStartTime());
        existingPlace.setShiftDuration(updatedPlace.getShiftDuration());
        return placeMapper.fromEntity(placeRepository.save(existingPlace));
    }
}
