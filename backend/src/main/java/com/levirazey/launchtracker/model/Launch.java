package com.levirazey.launchtracker.model;

import java.time.LocalDateTime;

public abstract class Launch {

    private String id;
    private String name;
    private String agency;
    private LocalDateTime net;
    private String status;

    public abstract String getSummary();

    public Launch(String id, String name, String agency, LocalDateTime net, String status) {
        this.id = id;
        this.name = name;
        this.agency = agency;
        this.net = net;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public LocalDateTime getNet() {
        return net;
    }

    public void setNet(LocalDateTime net) {
        this.net = net;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
