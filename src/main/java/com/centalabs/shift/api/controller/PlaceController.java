package com.centalabs.shift.api.controller;

import com.centalabs.shift.api.auth.AuthUserToken;
import com.centalabs.shift.api.domain.Place;
import com.centalabs.shift.api.dto.PageDTO;
import com.centalabs.shift.api.dto.PageRequestDTO;
import com.centalabs.shift.api.dto.PlaceDTO;
import com.centalabs.shift.api.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<PageDTO<PlaceDTO>> getAllPlaces(@ModelAttribute PageRequestDTO pageRequest) {
        return ResponseEntity.ok(placeService.getAllPlaces(pageRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDTO> updatePlace(@PathVariable UUID id, 
                                              @RequestBody PlaceDTO place) {
        return ResponseEntity.ok(placeService.updatePlace(id, place));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable UUID id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageDTO<PlaceDTO>> searchPlaces(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID ownerId,
            @ModelAttribute PageRequestDTO pageRequest) {
        return ResponseEntity.ok(placeService.searchPlaces(name, ownerId, pageRequest));
    }

    @PutMapping("/{id}/team-members")
    public ResponseEntity<PlaceDTO> updateTeamMembers(
            @PathVariable UUID id,
            @RequestBody List<UUID> teamMemberIds) {
        return ResponseEntity.ok(placeService.updateTeamMembers(id, teamMemberIds));
    }

    @GetMapping("/{id}/team-members")
    public ResponseEntity<List<UUID>> getTeamMembers(@PathVariable UUID id) {
        return ResponseEntity.ok(placeService.getTeamMembers(id));
    }

    @PutMapping("/{id}/configuration")
    public ResponseEntity<PlaceDTO> updatePlaceConfiguration(
            @PathVariable UUID id,
            @RequestBody PlaceDTO configuration) {
        return ResponseEntity.ok(placeService.updatePlaceConfiguration(id, configuration));
    }
}
