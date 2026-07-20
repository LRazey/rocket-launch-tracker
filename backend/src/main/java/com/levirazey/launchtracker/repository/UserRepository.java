package com.levirazey.launchtracker.repository;

import com.levirazey.launchtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    // Login: look up the account by username. Optional because it may not exist.
    Optional<User> findByUsername(String username);

    // Registration guards: reject a duplicate username or email before saving.
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
