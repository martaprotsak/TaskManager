package com.protsak.repository;

import com.protsak.entity.Task;
import com.protsak.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {

    Task findByIdAndUsersWithAccessContaining(String id, User user);
}
