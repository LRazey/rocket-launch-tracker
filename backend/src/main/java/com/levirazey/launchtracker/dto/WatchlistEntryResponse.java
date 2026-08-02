package com.levirazey.launchtracker.dto;

import com.levirazey.launchtracker.entity.WatchlistEntry;
import com.levirazey.launchtracker.model.Launch;

public class WatchlistEntryResponse {

    private Long id;
    private String launchId;
    private boolean reminderEnabled;
    private boolean watched;
    private final Launch launch;

    public static WatchlistEntryResponse from(WatchlistEntry entry, Launch launch) {
        return new WatchlistEntryResponse(entry.getId(), entry.getLaunchId(), entry.isReminderEnabled(), entry.isWatched(), launch);
    }

    public WatchlistEntryResponse(Long id, String launchId, boolean reminderEnabled, boolean watched, Launch launch) {
        this.id = id;
        this.launchId = launchId;
        this.reminderEnabled = reminderEnabled;
        this.watched = watched;
        this.launch = launch;
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
}
