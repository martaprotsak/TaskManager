package com.protsak.dto;

import com.protsak.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowTaskDto {

    private String title;

    private String description;

    private LocalDateTime dateTime;

    private String author;

    private List <String> sharedWith;
}
