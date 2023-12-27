package com.myblogp3.Repository;

import com.myblogp3.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);       // this is created bcz if email id is already exist signup is not performed

    Optional<User> findByUsername(String username);  // this is created bcz if username id is already exist signup is not performed

    Optional<User> findByUsernameOrEmail(String username, String email); // (above reason)

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
