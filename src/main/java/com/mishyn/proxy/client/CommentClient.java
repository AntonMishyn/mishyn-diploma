package com.mishyn.proxy.client;

import com.mishyn.proxy.dto.CommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mishyn.proxy.api.ApplicationAPI.*;

@FeignClient(name = "comments", url = "${ticketing_system.url}" + COMMENTS)
public interface CommentClient {

    @GetMapping
    ResponseEntity<List<CommentDTO>> getAllComments();

    @GetMapping(GET + "/{id}")
    ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id);

    @PutMapping(UPDATE)
    ResponseEntity<CommentDTO> updateCommentById(@RequestBody CommentDTO commentDTO);

    @PostMapping(CREATE)
    ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO);

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<?> deleteCommentById(@PathVariable Long id);
}
