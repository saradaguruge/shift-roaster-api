package com.centalabs.shift.api.controller;

import com.centalabs.shift.api.auth.AuthUserToken;
import com.centalabs.shift.api.domain.Place;
import com.centalabs.shift.api.dto.PlaceDTO;
import com.centalabs.shift.api.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceDTO place, AuthUserToken token) {
        return ResponseEntity.ok(placeService.createPlace(place, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable UUID id) {
        return ResponseEntity.ok(placeService.getPlaceById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlaceDTO>> getAllPlaces() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable UUID id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}
