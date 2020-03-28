package com.protsak.service;

import com.protsak.dto.ShareTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.entity.User;

import java.util.List;

public interface TaskService {

    TaskDto save(TaskDto task);

    TaskDto edit(TaskDto taskDto);

    String share(ShareTaskDto dto);

    String delete(String id);

    List<TaskDto> taskList(User user);
}
