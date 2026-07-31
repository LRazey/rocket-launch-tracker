package com.levirazey.launchtracker.service;

import com.levirazey.launchtracker.dto.WatchlistEntryResponse;
import com.levirazey.launchtracker.dto.WatchlistRequest;
import com.levirazey.launchtracker.dto.WatchlistUpdateRequest;
import com.levirazey.launchtracker.entity.User;
import com.levirazey.launchtracker.entity.WatchlistEntry;
import com.levirazey.launchtracker.exception.ResourceNotFoundException;
import com.levirazey.launchtracker.repository.UserRepository;
import com.levirazey.launchtracker.repository.WatchlistRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;

    public WatchlistService(WatchlistRepository watchlistRepository, UserRepository userRepository) {
        this.watchlistRepository = watchlistRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));
    }

    public List<WatchlistEntryResponse> getWatchlist(String username) {
        User user = getUser(username);

        return watchlistRepository.findByUser(user).stream()
                .map(WatchlistEntryResponse::from)
                .toList();
    }

    public WatchlistEntryResponse addToWatchlist(String username, WatchlistRequest request) {
        User user = getUser(username);
        if (watchlistRepository.existsByUserAndLaunchId(user, request.getLaunchId()))
            throw new IllegalArgumentException("That launch is already on your watchlist");
        WatchlistEntry entry = new WatchlistEntry(request.getLaunchId(), false, false, user);
        return WatchlistEntryResponse.from(watchlistRepository.save(entry));
    }

    public WatchlistEntryResponse updateEntry(String username, Long id, WatchlistUpdateRequest request) {
        User user = getUser(username);
        WatchlistEntry entry = watchlistRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist entry not found"));
        if (request.getReminderEnabled() != null) {
            entry.setReminderEnabled(request.getReminderEnabled());
        }
        if (request.getWatched() != null) {
            entry.setWatched(request.getWatched());
        }
        return WatchlistEntryResponse.from(watchlistRepository.save(entry));
    }

    public void deleteEntry(String username, Long id) {
        User user = getUser(username);
        WatchlistEntry entry = watchlistRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist entry not found"));
        watchlistRepository.delete(entry);
    }
}
