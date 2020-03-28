package com.protsak.mapper;

import com.protsak.dto.ShowTaskDto;
import com.protsak.entity.Task;
import com.protsak.entity.User;
import com.protsak.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowTaskMapper {

    private final UserService userService;

    public ShowTaskMapper(UserService userService) {
        this.userService = userService;
    }

    public ShowTaskDto convertToDto(Task model) {
        return ShowTaskDto.builder()
                .author(model.getAuthor().getUsername())
                .title(model.getTitle())
                .dateTime(model.getDateTime())
                .description(model.getDescription())
                .sharedWith(userNames(model.getUsersWithAccess()))
                .build();
    }

    public List<ShowTaskDto> convertToListDto(List<Task> tasks) {
        List<ShowTaskDto> listDto = new ArrayList<>();
        for (Task task : tasks) {
            listDto.add(convertToDto(task));
        }
        return listDto;
    }

    private List<String> userNames(List<User> users) {
        users.remove(userService.getCurrentUser());
        List<String> result = new ArrayList<>();
        for (User user : users) {
            result.add(user.getUsername());
        }
        return result;
    }
}
