package com.centalabs.shift.api.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String externalId;
    private String firstName;
    private String lastName;
    private String email;
}
