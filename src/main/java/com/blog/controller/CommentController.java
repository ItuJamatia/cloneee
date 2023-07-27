package com.blog.controller;

import com.blog.dto.CommentDto;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentDto> createComment
            (@RequestBody CommentDto commentDto, @PathVariable("postId") long postId) {

        CommentDto dto = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId) {

        List<CommentDto> dto = commentService.getCommentsByPostId(postId);
        return dto;
    }
    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentsByCommentId
    (@PathVariable("id") long id, @PathVariable("postId") long postId) {

        CommentDto dto = commentService.getCommentsByCommentId(id, postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable("id") long id,
                                                    @PathVariable("postId") long postId) {

        CommentDto dto = commentService.updateComment(id, postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{id}")
 //   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(@PathVariable("id") long id,
                                                @PathVariable("postId") long postId) {

        commentService.deleteComment(id, postId);
        return new ResponseEntity<>("Comment with id: " +id+ " is deleted", HttpStatus.OK);
    }
}
