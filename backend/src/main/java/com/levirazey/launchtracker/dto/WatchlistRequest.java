package com.levirazey.launchtracker.dto;

import jakarta.validation.constraints.NotBlank;

public class WatchlistRequest {

    @NotBlank
    private String launchId;

    public String getLaunchId() {
        return launchId;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }
}
