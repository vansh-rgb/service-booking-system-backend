package com.apiproject.ServiceBookingSystem.controller;

import com.apiproject.ServiceBookingSystem.dto.AuthenticationRequest;
import com.apiproject.ServiceBookingSystem.dto.SignupRequestDTO;
import com.apiproject.ServiceBookingSystem.dto.UserDto;
import com.apiproject.ServiceBookingSystem.entity.User;
import com.apiproject.ServiceBookingSystem.enums.ApiErrorCode;
import com.apiproject.ServiceBookingSystem.repository.UserRepository;
import com.apiproject.ServiceBookingSystem.services.authentication.AuthService;
import com.apiproject.ServiceBookingSystem.services.jwt.UserDetailsServiceImpl;
import com.apiproject.ServiceBookingSystem.util.JwtUtil;
import com.apiproject.ServiceBookingSystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO) {
        if(authService.presentByEmail(signupRequestDTO.getEmail())) {
            return ResponseUtil.buildErrorResponse(ApiErrorCode.CLIENT_EXISTS.getCode(),
                    ApiErrorCode.CLIENT_EXISTS.getMessage(), HttpStatus.CONFLICT);
        }

        UserDto createdUser = authService.signupClient(signupRequestDTO);
        return ResponseUtil.buildErrorResponse(ApiErrorCode.SUCCESSFUL_CLIENT_SIGNUP.getCode(),
                ApiErrorCode.SUCCESSFUL_CLIENT_SIGNUP.getMessage(), HttpStatus.OK);
    }

    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO) {
        if(authService.presentByEmail(signupRequestDTO.getEmail())) {
            return ResponseUtil.buildErrorResponse(ApiErrorCode.COMPANY_EXISTS.getCode(),
                    ApiErrorCode.COMPANY_EXISTS.getMessage(), HttpStatus.CONFLICT);        }

        UserDto createdUser = authService.signupCompany(signupRequestDTO);

        return ResponseUtil.buildResponse(ApiErrorCode.SUCCESSFUL_COMPANY_SIGNUP.getCode(),
                ApiErrorCode.SUCCESSFUL_COMPANY_SIGNUP.getMessage(), createdUser);
    }

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            success(authenticationRequest,response);

        } catch (BadCredentialsException e) {
            log.error("###################################");
            failure(authenticationRequest,response);
//            throw new BadCredentialsException("Invalid username or password", e);

        }
    }

    private void success(AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

        response.getWriter().write(new JSONObject()
                .put("userId", user.getId())
                .put("role", user.getRole())
                .put("code", ApiErrorCode.SUCCESSFUL_LOGIN.getCode())
                .put("message", ApiErrorCode.SUCCESSFUL_LOGIN.getMessage())
                .toString()
        );


        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization," +
                " X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-Header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }

    private void failure(AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set status to 401
        response.setContentType("application/json");
        response.getWriter().write(new JSONObject()
                .put("code", 3104) // Custom error code
                .put("message", "Invalid username or password.")
                .toString());
    }
}
