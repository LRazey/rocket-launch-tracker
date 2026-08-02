package com.levirazey.launchtracker.service;

import com.levirazey.launchtracker.dto.LaunchData;
import com.levirazey.launchtracker.model.CrewedLaunch;
import com.levirazey.launchtracker.model.Launch;
import com.levirazey.launchtracker.model.UncrewedLaunch;
import org.springframework.stereotype.Service;

@Service
public class LaunchFactory {

    public Launch createLaunch(LaunchData data) {
        if (data.isCrewed()) {
            return new CrewedLaunch(data.getId(), data.getName(), data.getAgency(), data.getNet(), data.getStatus(), data.getCrewRoster());
        } else {
            return new UncrewedLaunch(data.getId(), data.getName(), data.getAgency(), data.getNet(), data.getStatus(), data.getPayload());
        }
    }
}
