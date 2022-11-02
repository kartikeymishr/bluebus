package com.kartikeymishr.bluebusbackend.service;

import com.kartikeymishr.bluebusbackend.dto.model.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUser(UserDto userDto);

    void deleteUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto findByEmail(String email);
}
