package com.levirazey.launchtracker.model;

import java.util.List;
import java.time.LocalDateTime;

public class CrewedLaunch extends Launch {


    private List<String> crewRoster;

    public CrewedLaunch(String name, String agency, LocalDateTime net, String status, List<String> crewRoster) {
        super(name, agency, net, status);
        this.crewRoster = crewRoster;
    }

    @Override
    public String getSummary() {
        return getName() + ": crewed mission by " + getAgency() + " carrying " + crewRoster.size() + " crew. Status: " + getStatus();
    }

    public List<String> getCrewRoster() {
        return crewRoster;
    }
}
