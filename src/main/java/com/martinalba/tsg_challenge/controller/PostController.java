package com.martinalba.tsg_challenge.controller;

import com.martinalba.tsg_challenge.dto.request.PostUpdateRequest;
import com.martinalba.tsg_challenge.dto.response.PostResponse;
import com.martinalba.tsg_challenge.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse create(
            @RequestBody PostUpdateRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return postService.create(
                request.getTitle(),
                request.getContent(),
                extractUserId(jwt)
        );
    }

    @GetMapping
    public List<PostResponse> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PutMapping("/{id}")
    public PostResponse update(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return postService.update(id, request, extractUserId(jwt));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        postService.delete(id, extractUserId(jwt));
    }

    private Long extractUserId(Jwt jwt) {
        try {
            return Long.parseLong(jwt.getSubject());
        } catch (Exception e) {
            throw new AccessDeniedException("Invalid token");
        }
    }
}
