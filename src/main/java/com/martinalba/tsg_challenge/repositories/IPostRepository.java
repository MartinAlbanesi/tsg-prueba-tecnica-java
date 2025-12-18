package com.martinalba.tsg_challenge.repositories;

import com.martinalba.tsg_challenge.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
}
