package com.protsak.controller;

import com.protsak.dto.NewTaskDto;
import com.protsak.dto.ShareTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.service.TaskService;
import com.protsak.service.implementation.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update")
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
}