package com.martinalba.tsg_challenge.mapper;

import com.martinalba.tsg_challenge.dto.response.PostResponse;
import com.martinalba.tsg_challenge.entities.Post;

public class PostMapper {

    private PostMapper() {
    }

    public static PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorUsername(post.getAuthor().getUsername())
                .build();
    }
}
