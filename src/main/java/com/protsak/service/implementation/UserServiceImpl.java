package com.protsak.service.implementation;

import com.protsak.dto.UserRegistrationDto;
import com.protsak.entity.User;
import com.protsak.utils.ConstantMessage;
import com.protsak.exception.InvalidEmailException;
import com.protsak.exception.InvalidPasswordException;
import com.protsak.exception.UserAlreadyExistException;
import com.protsak.mapper.UserRegistrationMapper;
import com.protsak.repository.UserRepository;
import com.protsak.security.JWTUser;
import com.protsak.service.UserService;
import com.protsak.utils.Validator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserRegistrationMapper userRegistrationMapper;
    private Validator validator;

    public UserServiceImpl(UserRepository userRepository, UserRegistrationMapper userRegistrationMapper, Validator validator) {
        this.userRepository = userRepository;
        this.userRegistrationMapper = userRegistrationMapper;
        this.validator = validator;
    }

    @Override
    public void registration(UserRegistrationDto userRegistrationDto) {
        if (!validator.validateEmail(userRegistrationDto.getEmail())) {
            throw new InvalidEmailException(ConstantMessage.INVALID_EMAIL);
        }
        if (!validator.validatePassword(userRegistrationDto.getPassword())) {
            throw new InvalidPasswordException(ConstantMessage.INVALID_PASSWORD);
        }
        if (!(userRepository.findUserByEmail(userRegistrationDto.getEmail()) == null)) {
            throw new UserAlreadyExistException(ConstantMessage.USER_ALREADY_EXIST
                    + userRegistrationDto.getEmail());
        }
        User user = userRegistrationMapper.convertToEntity(userRegistrationDto);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUser jwtUser = (JWTUser) authentication.getPrincipal();
        return jwtUser.getUser();
    }

    @Override
    public boolean checkPasswordMatches(String id, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.findUserById(id);
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
