package com.levirazey.launchtracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlist_entry", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "launch_id"}))
public class WatchlistEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String launchId;

    @Column(nullable = false)
    private boolean reminderEnabled;

    @Column(nullable = false)
    private boolean watched;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public void toggleReminder() {
        this.reminderEnabled = !this.reminderEnabled;
    }

    protected WatchlistEntry() {}

    public WatchlistEntry(String launchId, boolean reminderEnabled, boolean watched, User user) {
        this.launchId = launchId;
        this.reminderEnabled = reminderEnabled;
        this.watched = watched;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getLaunchId() {
        return launchId;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }

    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(boolean reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
