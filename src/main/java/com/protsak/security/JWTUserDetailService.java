package com.protsak.security;

import com.protsak.entity.User;
import com.protsak.utils.ConstantMessage;
import com.protsak.exception.NotFoundException;
import com.protsak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public JWTUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new NotFoundException(ConstantMessage.USER_NOT_FOUND_BY_EMAIL + email);
        }
        return new JWTUser(user);
    }
}
