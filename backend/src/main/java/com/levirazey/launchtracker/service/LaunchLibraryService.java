package com.levirazey.launchtracker.service;

import com.levirazey.launchtracker.dto.LaunchData;
import com.levirazey.launchtracker.model.Launch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LaunchLibraryService {

    private final RestClient restClient;
    private final LaunchFactory launchFactory;

    public LaunchLibraryService(LaunchFactory launchFactory, @Value("${launchlibrary.base-url}") String baseUrl) {
        this.launchFactory = launchFactory;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<Launch> getUpcomingLaunches() {
        JsonNode root = restClient.get()
                .uri("/launches/upcoming/?format=json&limit=10")
                .retrieve()
                .body(JsonNode.class);

        return toLaunches(root);
    }

    public List<Launch> searchLaunches(String query) {
        JsonNode root = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/launches/upcoming/")
                        .queryParam("format", "json")
                        .queryParam("limit", "10")
                        .queryParam("search", query)
                        .build())
                .retrieve()
                .body(JsonNode.class);

        return toLaunches(root);
    }

    public Launch getLaunchById(String id) {
        JsonNode node = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/launches/" + id + "/")
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .body(JsonNode.class);

        if (node == null) {
            return null;
        }
        return launchFactory.createLaunch(parseLaunch(node));
    }

    private List<Launch> toLaunches(JsonNode root) {
        List<Launch> launches = new ArrayList<>();
        if (root == null) {
            return launches;
        }
        JsonNode results = root.get("results");
        if (results != null) {
            for (JsonNode node : results) {
                LaunchData data = parseLaunch(node);
                launches.add(launchFactory.createLaunch(data));
            }
        }
        return launches;
    }

    private LaunchData parseLaunch(JsonNode node) {
        String id = node.path("id").asString("");
        String name = node.path("name").asString("");
        String agency = node.path("launch_service_provider").path("name").asString("");
        String status = node.path("status").path("name").asString("");

        String netText = node.path("net").asString("");
        LocalDateTime net = netText.isEmpty() ? null : OffsetDateTime.parse(netText).toLocalDateTime();

        JsonNode mission = node.path("mission");
        String missionType = mission.path("type").asString("");
        boolean isCrewed = missionType.equalsIgnoreCase("Human Exploration");

        List<String> crewRoster = isCrewed ? new ArrayList<>() : null;
        String payload = isCrewed ? null : mission.path("name").asString("");

        return new LaunchData(id, name, agency, net, status, isCrewed, crewRoster, payload);
    }
}
