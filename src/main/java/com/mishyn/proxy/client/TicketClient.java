package com.mishyn.proxy.client;

import com.mishyn.proxy.dto.TicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.mishyn.proxy.api.ApplicationAPI.*;

@FeignClient(name = "tickets", url = "${ticketing_system.url}" + TICKETS)
public interface TicketClient {

    @GetMapping(WORKSPACES + "/{id}")
    ResponseEntity<Set<TicketDTO>> getAllTicketsByWorkspaceId(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<TicketDTO>> getAllTickets();

    @GetMapping(GET + "/{id}")
    ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id);

    @PutMapping(UPDATE)
    ResponseEntity<TicketDTO> updateTicketById(@RequestBody TicketDTO ticketDTO);

    @PostMapping(CREATE)
    ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO);

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<?> deleteTicketById(@PathVariable Long id);
}
