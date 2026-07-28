package com.levirazey.launchtracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public class LaunchData {

    private String name;
    private String agency;
    private LocalDateTime net;
    private String status;
    private boolean isCrewed;
    private List<String> crewRoster;
    private String payload;

    public LaunchData(String name, String agency, LocalDateTime net, String status, boolean isCrewed, List<String> crewRoster, String payload) {
        this.name = name;
        this.agency = agency;
        this.net = net;
        this.status = status;
        this.isCrewed = isCrewed;
        this.crewRoster = crewRoster;
        this.payload = payload;
    }

    public String getName() {
        return name;
    }

    public String getAgency() {
        return agency;
    }

    public LocalDateTime getNet() {
        return net;
    }

    public String getStatus() {
        return status;
    }

    public boolean isCrewed() {
        return isCrewed;
    }

    public List<String> getCrewRoster() {
        return crewRoster;
    }

    public String getPayload() {
        return payload;
    }
}
