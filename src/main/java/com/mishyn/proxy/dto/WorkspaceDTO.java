package com.mishyn.proxy.dto;

import com.mishyn.proxy.dto.TicketDTO;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDTO {

    private Long id;

    private String name;

    private Set<TicketDTO> ticketEntities;
}
