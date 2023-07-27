package com.blog.repository;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<CommentDto> findByPostId(long postId);
}
