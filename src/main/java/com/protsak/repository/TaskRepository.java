package com.protsak.repository;

import com.protsak.entity.Task;
import com.protsak.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    Task findByTitleAndUsersWithAccessContaining(String title, User user);

    List<Task> findAllByUsersWithAccessContaining(User user);
}
