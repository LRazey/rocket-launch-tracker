package com.levirazey.launchtracker.dto;

import com.levirazey.launchtracker.entity.WatchlistEntry;
import com.levirazey.launchtracker.model.Launch;

import java.time.LocalDateTime;

public class WatchlistEntryResponse {

    private Long id;
    private String launchId;
    private boolean reminderEnabled;
    private boolean watched;
    private final Launch launch;
    private LocalDateTime savedDate;

    public static WatchlistEntryResponse from(WatchlistEntry entry, Launch launch) {
        return new WatchlistEntryResponse(entry.getId(), entry.getLaunchId(), entry.isReminderEnabled(), entry.isWatched(), launch, entry.getSavedDate());
    }

    public WatchlistEntryResponse(Long id, String launchId, boolean reminderEnabled, boolean watched, Launch launch, LocalDateTime savedDate) {
        this.id = id;
        this.launchId = launchId;
        this.reminderEnabled = reminderEnabled;
        this.watched = watched;
        this.launch = launch;
        this.savedDate = savedDate;
    }

    public Long getId() {
        return id;
    }

    public String getLaunchId() {
        return launchId;
    }

    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    public boolean isWatched() {
        return watched;
    }

    public Launch getLaunch() {
        return launch;
    }

    public LocalDateTime getSavedDate() {
        return savedDate;
    }
}
