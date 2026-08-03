package com.levirazey.launchtracker.service;

import com.levirazey.launchtracker.dto.LaunchData;
import com.levirazey.launchtracker.model.CrewedLaunch;
import com.levirazey.launchtracker.model.Launch;
import com.levirazey.launchtracker.model.UncrewedLaunch;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaunchFactoryTest {

    @Test
    void createLaunch_returnsCrewedLaunch_whenDataIsCrewed() {
        LaunchFactory factory = new LaunchFactory();
        LaunchData data = new LaunchData(
                "test-id-1",
                "Artemis III",
                "NASA",
                LocalDateTime.now().plusDays(30),
                "Go for Launch",
                true,
                List.of("Commander", "Pilot"),
                null
        );

        Launch result = factory.createLaunch(data);

        assertInstanceOf(CrewedLaunch.class, result);
        assertTrue(result.getSummary().contains("carrying"));
    }

    @Test
    void createLaunch_returnsUncrewedLaunch_whenDataIsUncrewed() {
        LaunchFactory factory = new LaunchFactory();
        LaunchData data = new LaunchData(
                "test-id-2",
                "Starlink",
                "SpaceX",
                LocalDateTime.now().plusDays(30),
                "Go for Launch",
                false,
                null,
                "StarLink Group 1-50"
        );

        Launch result = factory.createLaunch(data);

        assertInstanceOf(UncrewedLaunch.class, result);
        assertTrue(result.getSummary().contains("payload of"));
    }
}
