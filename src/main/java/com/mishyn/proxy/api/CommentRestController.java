package com.mishyn.proxy.api;

import com.mishyn.proxy.client.CommentClient;
import com.mishyn.proxy.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mishyn.proxy.api.ApplicationAPI.*;


@RestController
@RequestMapping(COMMENTS)
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentClient commentClient;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return commentClient.getAllComments();
    }

    @GetMapping(GET + "/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return commentClient.getCommentById(id);
    }

    @PutMapping(UPDATE)
    public ResponseEntity<CommentDTO> updateCommentById(@RequestBody CommentDTO commentDTO) {
        return commentClient.updateCommentById(commentDTO);
    }

    @PostMapping(CREATE)
    public ResponseEntity<CommentDTO> createCommentById(@RequestBody CommentDTO commentDTO) {
        return commentClient.createComment(commentDTO);
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        return commentClient.deleteCommentById(id);
    }
}
