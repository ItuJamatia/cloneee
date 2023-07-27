package com.blog.service;

import com.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {


    CommentDto createComment(CommentDto commentDto, long postId);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentsByCommentId(long id, long postId);

    CommentDto updateComment(long id, long postId, CommentDto commentDto);

    void deleteComment(long id, long postId);
}
