package org.example.server.model;

public class User {
    private String username;
    private String address;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}