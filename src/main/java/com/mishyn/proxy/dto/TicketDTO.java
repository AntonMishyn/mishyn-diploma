package com.mishyn.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;

    private String title;

    private String body;

    private Long workspaceId;

    private UserDTO createdBy;

    private UserDTO assignee;

    private String status;

    private LocalDateTime dueDate;

    private Set<CommentDTO> comments;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
