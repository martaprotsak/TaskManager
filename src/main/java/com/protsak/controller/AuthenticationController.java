package com.protsak.controller;


import com.protsak.dto.AuthenticationDto;
import com.protsak.entity.User;
import com.protsak.utils.ConstantMessage;
import com.protsak.exception.InvalidPasswordException;
import com.protsak.exception.NotFoundException;
import com.protsak.security.CookieProvider;
import com.protsak.security.JWTTokenProvider;
import com.protsak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private JWTTokenProvider jwtTokenProvider;
    private UserService userService;
    private CookieProvider cookieProvider;

    @Autowired
    public AuthenticationController(JWTTokenProvider jwtTokenProvider,
                                    UserService userService,
                                    CookieProvider cookieProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.cookieProvider = cookieProvider;
    }

    @PostMapping("/sign-in")
    public void login(@RequestBody AuthenticationDto authenticationDto, HttpServletResponse response) {
        User user = userService.findUserByEmail(authenticationDto.getEmail());
        if (user != null) {
            if (userService.checkPasswordMatches(user.getId(), authenticationDto.getPassword())) {
                Cookie cookie = cookieProvider.createCookie(
                        "jwt",
                        jwtTokenProvider.generateToken(user.getId(), user.getEmail()));
                response.addCookie(cookie);
            } else {
                throw new InvalidPasswordException(ConstantMessage.INCORRECT_PASSWORD);
            }
        } else {
            throw new NotFoundException(ConstantMessage.USER_NOT_FOUND_BY_EMAIL);
        }
    }
}