package com.levirazey.launchtracker.dto;

import com.levirazey.launchtracker.entity.User;

public class UserResponse {

    private final Long id;
    private final String username;
    private final String email;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public UserResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
