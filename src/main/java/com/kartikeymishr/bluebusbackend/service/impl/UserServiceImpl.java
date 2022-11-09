package com.kartikeymishr.bluebusbackend.service.impl;

import com.kartikeymishr.bluebusbackend.dto.mapper.UserMapper;
import com.kartikeymishr.bluebusbackend.dto.model.user.UserDto;
import com.kartikeymishr.bluebusbackend.dto.request.LoginRequest;
import com.kartikeymishr.bluebusbackend.model.user.User;
import com.kartikeymishr.bluebusbackend.repository.user.UserRepository;
import com.kartikeymishr.bluebusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            user = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());

            return UserMapper.toUserDto(userRepository.save(user));
        }

        return null;
    }

    @Override
    public UserDto getUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(UserDto userDto) {

    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto findByEmail(String email) {
        return UserMapper.toUserDto(userRepository.findByEmail(email));
    }

    @Override
    public UserDto validateUser(LoginRequest loginRequest) {
        UserDto user = findByEmail(loginRequest.getEmail());
        if (user != null && bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return user;
        }

        throw new RuntimeException("Incorrect Password");
    }
}
