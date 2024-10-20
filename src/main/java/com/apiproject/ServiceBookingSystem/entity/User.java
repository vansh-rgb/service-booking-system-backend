package com.apiproject.ServiceBookingSystem.entity;

import com.apiproject.ServiceBookingSystem.dto.UserDto;
import com.apiproject.ServiceBookingSystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phone;
    private UserRole role;

    public UserDto getDto() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail(email);
        userDto.setName(name);
        userDto.setRole(role);
        return userDto;
    }


}