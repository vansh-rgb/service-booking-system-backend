package com.apiproject.ServiceBookingSystem.dto;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;

}
