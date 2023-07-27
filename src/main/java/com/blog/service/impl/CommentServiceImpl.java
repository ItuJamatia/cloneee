package com.blog.service.impl;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Comment comment = mapToEntity(commentDto);

        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment with postId: " + postId + "not found")
        );
        // set post to comment entity
        comment.setPost(post);
        // comment entity to DB
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        List<CommentDto> dto = commentRepository.findByPostId(postId);
        return dto;
    }
    @Override
    public CommentDto getCommentsByCommentId(long id, long postId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment with postId: " + postId + "not found")
        );
        Comment comment= commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment with postId: " + postId + "not found")
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to postId:" +postId);
        }
        return mapToDto(comment);
    }
    @Override
    public CommentDto updateComment(long id,long postId,CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment with postId: " + postId + "not found")
        );
        Comment comment= commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment with id: " + id + "not found")
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to postId:" +postId);
        }
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setReview(commentDto.getReview());

        Comment updatedComment= commentRepository.save(comment);
        return mapToDto(updatedComment);
    }
    @Override
    public void deleteComment(long id, long postId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment with postId: " + postId + "not found")
        );
        Comment comment= commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment with id: " + id + "not found")
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to postId:" +postId);
        }
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment newComment) {
        return mapper.map(newComment,CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

}
