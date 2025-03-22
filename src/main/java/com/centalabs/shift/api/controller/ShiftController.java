package com.centalabs.shift.api.controller;

import com.centalabs.shift.api.auth.AuthUserToken;
import com.centalabs.shift.api.domain.Shift;
import com.centalabs.shift.api.dto.PageDTO;
import com.centalabs.shift.api.dto.PageRequestDTO;
import com.centalabs.shift.api.dto.ShiftDTO;
import com.centalabs.shift.api.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/places/{placeId}/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @PostMapping
    public ResponseEntity<ShiftDTO> createShift(@PathVariable UUID placeId,
                                              @RequestBody ShiftDTO shift, 
                                              AuthUserToken token) {
        return ResponseEntity.ok(shiftService.createShift(placeId, shift, token));
    }

    @GetMapping
    public ResponseEntity<PageDTO<ShiftDTO>> getShiftsByPlace(
            @PathVariable UUID placeId,
            @ModelAttribute PageRequestDTO pageRequest) {
        return ResponseEntity.ok(shiftService.getShiftsByPlace(placeId, pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDTO> getShiftById(
            @PathVariable UUID placeId,
            @PathVariable UUID id) {
        return ResponseEntity.ok(shiftService.getShiftById(placeId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftDTO> updateShift(
            @PathVariable UUID placeId,
            @PathVariable UUID id, 
            @RequestBody ShiftDTO shift) {
        return ResponseEntity.ok(shiftService.updateShift(placeId, id, shift));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(
            @PathVariable UUID placeId,
            @PathVariable UUID id) {
        shiftService.deleteShift(placeId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageDTO<ShiftDTO>> searchShifts(
            @PathVariable UUID placeId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID assignedTo,
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate,
            @ModelAttribute PageRequestDTO pageRequest) {
        return ResponseEntity.ok(shiftService.searchShifts(placeId, status, assignedTo, startDate, endDate, pageRequest));
    }
} 