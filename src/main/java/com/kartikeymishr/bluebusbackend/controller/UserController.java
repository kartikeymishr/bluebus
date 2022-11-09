package com.kartikeymishr.bluebusbackend.controller;

import com.kartikeymishr.bluebusbackend.dto.model.user.UserDto;
import com.kartikeymishr.bluebusbackend.dto.request.LoginRequest;
import com.kartikeymishr.bluebusbackend.dto.request.SignupRequest;
import com.kartikeymishr.bluebusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignupRequest signupRequest) throws Exception {
        UserDto userDto = new UserDto()
                .setEmail(signupRequest.getEmail())
                .setPassword(signupRequest.getPassword())
                .setFirstName(signupRequest.getFirstName())
                .setLastName(signupRequest.getLastName())
                .setMobileNumber(signupRequest.getMobileNumber());

        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.validateUser(loginRequest));
    }
}
