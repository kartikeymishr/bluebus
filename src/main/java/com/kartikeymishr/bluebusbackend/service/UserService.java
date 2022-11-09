package com.kartikeymishr.bluebusbackend.service;

import com.kartikeymishr.bluebusbackend.dto.model.user.UserDto;
import com.kartikeymishr.bluebusbackend.dto.request.LoginRequest;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUser(UserDto userDto);

    void deleteUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto findByEmail(String email);

    UserDto validateUser(LoginRequest loginRequest);
}
