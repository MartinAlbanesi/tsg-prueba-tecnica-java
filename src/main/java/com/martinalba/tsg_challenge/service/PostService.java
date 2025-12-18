package com.martinalba.tsg_challenge.service;

import com.martinalba.tsg_challenge.dto.request.PostUpdateRequest;
import com.martinalba.tsg_challenge.dto.response.PostResponse;
import com.martinalba.tsg_challenge.entities.Post;
import com.martinalba.tsg_challenge.entities.User;
import com.martinalba.tsg_challenge.mapper.PostMapper;
import com.martinalba.tsg_challenge.repositories.IPostRepository;
import com.martinalba.tsg_challenge.repositories.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    public PostResponse create(String title, String content, Long userId) {

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Post post = postRepository.save(
                Post.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .build()
        );

        return PostMapper.toResponse(post);
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toResponse)
                .toList();
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        return PostMapper.toResponse(post);
    }

    public PostResponse update(Long postId, PostUpdateRequest request, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        authorize(post, userId);

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return PostMapper.toResponse(postRepository.save(post));
    }

    public void delete(Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        authorize(post, userId);

        postRepository.delete(post);
    }

    private void authorize(Post post, Long userId) {
        if (!post.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to modify this post");
        }
    }
}

