package com.protsak.mapper;

import com.protsak.dto.UserRegistrationDto;
import com.protsak.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

    public User convertToEntity(UserRegistrationDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}