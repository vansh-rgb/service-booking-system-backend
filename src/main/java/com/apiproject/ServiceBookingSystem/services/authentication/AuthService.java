package com.apiproject.ServiceBookingSystem.services.authentication;

import com.apiproject.ServiceBookingSystem.dto.SignupRequestDTO;
import com.apiproject.ServiceBookingSystem.dto.UserDto;

public interface AuthService {

    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    UserDto signupCompany(SignupRequestDTO signupRequestDTO);

    Boolean presentByEmail(String email);

}
