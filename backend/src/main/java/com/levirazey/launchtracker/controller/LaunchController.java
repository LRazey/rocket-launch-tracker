package com.levirazey.launchtracker.controller;

import com.levirazey.launchtracker.model.Launch;
import com.levirazey.launchtracker.service.LaunchLibraryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/launches")
public class LaunchController {

    private final LaunchLibraryService launchLibraryService;

    public LaunchController(LaunchLibraryService launchLibraryService) {
        this.launchLibraryService = launchLibraryService;
    }

    @GetMapping("/upcoming")
    public List<Launch> getUpcoming() {
        return launchLibraryService.getUpcomingLaunches();
    }

    @GetMapping("/search")
    public List<Launch> search(@RequestParam String q) {
        return launchLibraryService.searchLaunches(q);
    }
}
