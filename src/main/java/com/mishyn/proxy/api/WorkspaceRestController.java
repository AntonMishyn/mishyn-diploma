package com.mishyn.proxy.api;

import com.mishyn.proxy.client.WorkspaceClient;
import com.mishyn.proxy.dto.WorkspaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mishyn.proxy.api.ApplicationAPI.*;

@RestController
@RequestMapping(WORKSPACES)
@RequiredArgsConstructor
public class WorkspaceRestController {

    private final WorkspaceClient workspaceClient;

    @GetMapping
    ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        return workspaceClient.getAllWorkspaces();
    }

    @GetMapping(GET + "/{id}")
    ResponseEntity<WorkspaceDTO> getWorkspaceById(@PathVariable Long id) {
        return workspaceClient.getWorkspaceById(id);
    }

    @PutMapping(UPDATE)
    ResponseEntity<WorkspaceDTO> updateWorkspaceById(@RequestBody WorkspaceDTO workspaceDTO) {
        return workspaceClient.updateWorkspaceById(workspaceDTO);
    }

    @PostMapping(CREATE)
    ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody WorkspaceDTO workspaceDTO) {
        return workspaceClient.createWorkspace(workspaceDTO);
    }

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<?> deleteWorkspaceById(@PathVariable Long id) {
        return workspaceClient.deleteWorkspaceById(id);
    }
}
