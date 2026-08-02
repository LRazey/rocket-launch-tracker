package com.levirazey.launchtracker.service;

import com.levirazey.launchtracker.dto.WatchlistEntryResponse;
import com.levirazey.launchtracker.dto.WatchlistRequest;
import com.levirazey.launchtracker.dto.WatchlistUpdateRequest;
import com.levirazey.launchtracker.entity.User;
import com.levirazey.launchtracker.entity.WatchlistEntry;
import com.levirazey.launchtracker.exception.ResourceNotFoundException;
import com.levirazey.launchtracker.model.Launch;
import com.levirazey.launchtracker.repository.UserRepository;
import com.levirazey.launchtracker.repository.WatchlistRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final LaunchLibraryService launchLibraryService;

    public WatchlistService(WatchlistRepository watchlistRepository, UserRepository userRepository, LaunchLibraryService launchLibraryService) {
        this.watchlistRepository = watchlistRepository;
        this.userRepository = userRepository;
        this.launchLibraryService = launchLibraryService;
    }

    private Launch safeGetLaunch(String launchId) {
        try {
            return launchLibraryService.getLaunchById(launchId);
        } catch (Exception e) {
            System.out.println("Launch lookup failed for " + launchId + ": " + e.getMessage());
            return null;
        }
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));
    }

    public List<WatchlistEntryResponse> getWatchlist(String username) {
        User user = getUser(username);

        return watchlistRepository.findByUser(user).stream()
                .map(entry -> WatchlistEntryResponse.from(entry, safeGetLaunch(entry.getLaunchId())))
                .toList();
    }

    public WatchlistEntryResponse addToWatchlist(String username, WatchlistRequest request) {
        User user = getUser(username);
        if (watchlistRepository.existsByUserAndLaunchId(user, request.getLaunchId()))
            throw new IllegalArgumentException("That launch is already on your watchlist");
        WatchlistEntry entry = new WatchlistEntry(request.getLaunchId(), false, false, user);
        WatchlistEntry saved = watchlistRepository.save(entry);
        return WatchlistEntryResponse.from(saved, safeGetLaunch(saved.getLaunchId()));
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
        WatchlistEntry saved = watchlistRepository.save(entry);
        return WatchlistEntryResponse.from(saved, safeGetLaunch(saved.getLaunchId()));
    }

    public void deleteEntry(String username, Long id) {
        User user = getUser(username);
        WatchlistEntry entry = watchlistRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist entry not found"));
        watchlistRepository.delete(entry);
    }
}
