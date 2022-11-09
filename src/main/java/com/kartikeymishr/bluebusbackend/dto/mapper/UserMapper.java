package com.kartikeymishr.bluebusbackend.dto.mapper;

import com.kartikeymishr.bluebusbackend.dto.model.user.UserDto;
import com.kartikeymishr.bluebusbackend.model.user.User;

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
