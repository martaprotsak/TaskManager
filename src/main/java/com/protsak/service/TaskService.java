package com.protsak.service;

import com.protsak.dto.NewTaskDto;
import com.protsak.dto.ShareTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.entity.Task;

public interface TaskService {

     TaskDto save(NewTaskDto task);

     TaskDto edit(TaskDto taskDto);

     String share(ShareTaskDto dto);
}
