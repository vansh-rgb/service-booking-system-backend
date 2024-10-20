package com.apiproject.ServiceBookingSystem.services.authentication;

import com.apiproject.ServiceBookingSystem.dto.SignupRequestDTO;
import com.apiproject.ServiceBookingSystem.dto.UserDto;
import com.apiproject.ServiceBookingSystem.entity.User;
import com.apiproject.ServiceBookingSystem.enums.UserRole;
import com.apiproject.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;


    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(signupRequestDTO.getPassword());
        user.setName(signupRequestDTO.getName());
        user.setPhone(signupRequestDTO.getPhone());
        user.setLastName(signupRequestDTO.getLastName());

        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }
}
