package com.apiproject.ServiceBookingSystem.dto;

import com.apiproject.ServiceBookingSystem.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequestDTO {

    private long id;

    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phone;
    private UserRole role;

}
