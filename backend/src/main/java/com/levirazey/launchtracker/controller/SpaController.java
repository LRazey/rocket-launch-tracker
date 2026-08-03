package com.levirazey.launchtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    @RequestMapping({"/", "/launches", "/login", "/register", "/watchlist", "/report"})
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
