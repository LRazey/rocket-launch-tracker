package com.levirazey.launchtracker.dto;

public class WatchlistUpdateRequest {

    private Boolean reminderEnabled;
    private Boolean watched;

    public Boolean getWatched() {
        return watched;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    public Boolean getReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(Boolean reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }
}
