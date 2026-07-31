package com.levirazey.launchtracker.repository;

import com.levirazey.launchtracker.entity.User;
import com.levirazey.launchtracker.entity.WatchlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<WatchlistEntry, Long> {

    List<WatchlistEntry> findByUser(User user);

    Optional<WatchlistEntry> findByIdAndUser(Long id, User user);

    boolean existsByUserAndLaunchId(User user, String launchId);
}
