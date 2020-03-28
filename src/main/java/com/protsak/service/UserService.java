package com.protsak.service;

import com.protsak.dto.UserRegistrationDto;
import com.protsak.entity.User;

public interface UserService {

    User findUserByEmail(String email);

    boolean checkPasswordMatches(String id, String password);

    void registration(UserRegistrationDto dto);

    User getCurrentUser();
}
