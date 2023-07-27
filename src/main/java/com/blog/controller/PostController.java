package com.blog.controller;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //http://localhost:8080/api/posts
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto dto= postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts?pageNo=1&pageSize=3&soryBy=title&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
         @RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
          @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
           @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){

        return postService.getAllPosts(pageNo,pageSize, sortBy, sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable ("id") long id){
        PostDto dto= postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable ("id") long id){
        PostDto dto= postService.updatePost(postDto,id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1
    @DeleteMapping("/{id}")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable ("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post with id: " +id+ " is deleted ", HttpStatus.OK);
    }
}
