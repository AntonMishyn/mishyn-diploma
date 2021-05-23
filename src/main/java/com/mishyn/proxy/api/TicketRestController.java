package com.mishyn.proxy.api;

import com.mishyn.proxy.client.TicketClient;
import com.mishyn.proxy.dto.TicketDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.mishyn.proxy.api.ApplicationAPI.*;

@RestController
@RequestMapping(TICKETS)
@RequiredArgsConstructor
@Slf4j
public class TicketRestController {

    private final TicketClient ticketClient;

    @GetMapping(WORKSPACES + "/{id}")
    ResponseEntity<Set<TicketDTO>> getAllTicketsByWorkspaceId(@PathVariable Long id){
        return ticketClient.getAllTicketsByWorkspaceId(id);
    }

    @GetMapping
    ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ticketClient.getAllTickets();
    }

    @GetMapping(GET + "/{id}")
    ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        return ticketClient.getTicketById(id);
    }

    @PutMapping(UPDATE)
    ResponseEntity<TicketDTO> updateTicketById(@RequestBody TicketDTO ticketDTO) {
        return ticketClient.updateTicketById(ticketDTO);
    }

    @PostMapping(CREATE)
    ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        return ticketClient.createTicket(ticketDTO);
    }

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<?> deleteTicketById(@PathVariable Long id) {
        return ticketClient.deleteTicketById(id);
    }
}
