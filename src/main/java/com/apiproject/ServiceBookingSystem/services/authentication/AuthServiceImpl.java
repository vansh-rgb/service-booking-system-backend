package com.apiproject.ServiceBookingSystem.services.authentication;

import com.apiproject.ServiceBookingSystem.dto.SignupRequestDTO;
import com.apiproject.ServiceBookingSystem.dto.UserDto;
import com.apiproject.ServiceBookingSystem.entity.User;
import com.apiproject.ServiceBookingSystem.enums.UserRole;
import com.apiproject.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;


//    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
//        User user = new User();
//        if(signupRequestDTO.getEmail()!=null && signupRequestDTO.getPassword()!=null ) {
//
//            user.setEmail(signupRequestDTO.getEmail());
//            user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
//            user.setName(signupRequestDTO.getName());
//            user.setPhone(signupRequestDTO.getPhone());
//            user.setLastName(signupRequestDTO.getLastName());
//
//            user.setRole(UserRole.CLIENT);
//
//            return userRepository.save(user).getDto();
//        }
//        else
//        {
//
//        }
//    }


    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        if (signupRequestDTO.getEmail() == null || signupRequestDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (signupRequestDTO.getPassword() == null || signupRequestDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }

        // Create a new User entity and set the properties
        User user = new User();
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setName(signupRequestDTO.getName());
        user.setPhone(signupRequestDTO.getPhone());
        user.setLastName(signupRequestDTO.getLastName());
        user.setRole(UserRole.CLIENT);

        // Save the user to the repository
        return userRepository.save(user).getDto();
    }

    public Boolean presentByEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    //    public UserDto signupCompany(SignupRequestDTO signupRequestDTO) {
//        User user = new User();
//        user.setEmail(signupRequestDTO.getEmail());
//        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
//        user.setName(signupRequestDTO.getName());
//        user.setPhone(signupRequestDTO.getPhone());
//
//        user.setRole(UserRole.COMPANY);
//
//        return userRepository.save(user).getDto();
//    }
//
//}
    public UserDto signupCompany(SignupRequestDTO signupRequestDTO) {
        if (signupRequestDTO.getEmail() == null || signupRequestDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (signupRequestDTO.getPassword() == null || signupRequestDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }

        // Create a new User entity and set the properties
        User user = new User();
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setName(signupRequestDTO.getName());
        user.setPhone(signupRequestDTO.getPhone());
        user.setRole(UserRole.COMPANY);

        // Save the user to the repository
        return userRepository.save(user).getDto();
    }
}
