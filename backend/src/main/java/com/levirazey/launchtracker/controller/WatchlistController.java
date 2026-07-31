package com.levirazey.launchtracker.controller;

import com.levirazey.launchtracker.dto.WatchlistEntryResponse;
import com.levirazey.launchtracker.dto.WatchlistRequest;
import com.levirazey.launchtracker.dto.WatchlistUpdateRequest;
import com.levirazey.launchtracker.service.WatchlistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @GetMapping
    public List<WatchlistEntryResponse> getWatchlist(@AuthenticationPrincipal UserDetails principal) {
        return watchlistService.getWatchlist(principal.getUsername());
    }

    @PostMapping
    public ResponseEntity<WatchlistEntryResponse> add(
            @AuthenticationPrincipal UserDetails principal, @Valid @RequestBody WatchlistRequest request) {
        
        WatchlistEntryResponse saved = watchlistService.addToWatchlist(principal.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PatchMapping("/{id}")
    public WatchlistEntryResponse update(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id,
            @RequestBody WatchlistUpdateRequest request) {

        return watchlistService.updateEntry(principal.getUsername(), id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id) {

        watchlistService.deleteEntry(principal.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}
