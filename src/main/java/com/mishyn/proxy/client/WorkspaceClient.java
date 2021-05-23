package com.mishyn.proxy.client;

import com.mishyn.proxy.dto.WorkspaceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mishyn.proxy.api.ApplicationAPI.*;

@FeignClient(name = "workspaces", url = "${ticketing_system.url}" + WORKSPACES)
public interface WorkspaceClient {

    @GetMapping
    ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces();

    @GetMapping(GET + "/{id}")
    ResponseEntity<WorkspaceDTO> getWorkspaceById(@PathVariable Long id);

    @PutMapping(UPDATE)
    ResponseEntity<WorkspaceDTO> updateWorkspaceById(@RequestBody WorkspaceDTO WorkspaceDTO);

    @PostMapping(CREATE)
    ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody WorkspaceDTO WorkspaceDTO);

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<?> deleteWorkspaceById(@PathVariable Long id);
}
