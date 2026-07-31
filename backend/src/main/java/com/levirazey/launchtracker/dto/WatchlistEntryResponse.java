package com.levirazey.launchtracker.dto;

import com.levirazey.launchtracker.entity.WatchlistEntry;

public class WatchlistEntryResponse {

    private Long id;
    private String launchId;
    private boolean reminderEnabled;
    private boolean watched;

    public static WatchlistEntryResponse from(WatchlistEntry entry) {
        return new WatchlistEntryResponse(entry.getId(), entry.getLaunchId(), entry.isReminderEnabled(), entry.isWatched());
    }

    public WatchlistEntryResponse(Long id, String launchId, boolean reminderEnabled, boolean watched) {
        this.id = id;
        this.launchId = launchId;
        this.reminderEnabled = reminderEnabled;
        this.watched = watched;
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
}
