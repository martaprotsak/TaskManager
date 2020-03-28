package com.protsak.repository;

import com.protsak.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(String email);

    User findUserById(String id);
}
