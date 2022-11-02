package com.kartikeymishr.bluebusbackend.dto.mapper;

import com.kartikeymishr.bluebusbackend.dto.model.UserDto;
import com.kartikeymishr.bluebusbackend.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber());
    }
}
