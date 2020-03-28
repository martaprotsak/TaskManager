package com.protsak.controller;

import com.protsak.dto.ShareTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.service.TaskService;
import com.protsak.service.UserService;
import com.protsak.service.implementation.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskServiceImpl taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping
    public TaskDto save(@RequestBody TaskDto taskDto) {
        return taskService.save(taskDto);
    }

    @PutMapping
    public TaskDto update(@RequestBody TaskDto taskDto) {
        return taskService.edit(taskDto);
    }

    @PostMapping("/share")
    public String shareTask(@RequestBody ShareTaskDto dto) {
        return taskService.share(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        return new ResponseEntity<>(taskService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<TaskDto> showAllTasks() {
        return taskService.taskList(userService.getCurrentUser());
    }
}