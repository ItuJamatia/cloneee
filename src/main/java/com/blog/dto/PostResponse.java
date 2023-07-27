package com.blog.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean isLast;
}
