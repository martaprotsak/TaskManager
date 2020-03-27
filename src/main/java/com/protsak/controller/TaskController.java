package com.protsak.controller;

import com.protsak.dto.NewTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.service.TaskService;
import com.protsak.service.implementation.TaskServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskDto save(@RequestBody NewTaskDto newTaskDto) {
        return taskService.save(newTaskDto);
    }
}
