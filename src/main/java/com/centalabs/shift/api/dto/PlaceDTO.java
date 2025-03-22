package com.centalabs.shift.api.dto;

import com.centalabs.shift.api.domain.User;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PlaceDTO {
    private String name;
    private ZonedDateTime shiftStartTime;
    private int shiftDuration;
    private UserDTO owner;

}
