package com.kartikeymishr.bluebusbackend.controller;

import com.kartikeymishr.bluebusbackend.dto.model.UserDto;
import com.kartikeymishr.bluebusbackend.dto.request.UserSignupRequest;
import com.kartikeymishr.bluebusbackend.dto.response.Response;
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
    public ResponseEntity<Response<UserDto>> signUpUser(@RequestBody UserSignupRequest userSignupRequest) throws Exception {
        UserDto userDto = new UserDto()
                .setEmail(userSignupRequest.getEmail())
                .setPassword(userSignupRequest.getPassword())
                .setFirstName(userSignupRequest.getFirstName())
                .setLastName(userSignupRequest.getLastName())
                .setMobileNumber(userSignupRequest.getMobileNumber());

        Response<UserDto> response = new Response<UserDto>().setPayload(userService.createUser(userDto));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<UserDto>> loginUser(@RequestBody UserSignupRequest userSignupRequest) {
        return null;
    }
}
