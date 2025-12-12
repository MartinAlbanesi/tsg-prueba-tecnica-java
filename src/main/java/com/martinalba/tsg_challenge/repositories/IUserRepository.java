package com.martinalba.tsg_challenge.repositories;

import com.martinalba.tsg_challenge.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
