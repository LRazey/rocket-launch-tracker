package com.levirazey.launchtracker.model;

import java.time.LocalDateTime;

public class UncrewedLaunch extends Launch {

    private String payload;

    public UncrewedLaunch(String id, String name, String agency, LocalDateTime net, String status, String payload) {
        super(id, name, agency, net, status);
        this.payload = payload;
    }

    @Override
    public String getSummary() {
        return getName() + ": Uncrewed mission by " + getAgency() + " with a payload of " + getPayload() + ". Status: " + getStatus();
    }

    public String getPayload() {
        return payload;
    }
}
