package com.santacarolina.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private long id;
    private String username;
    private String password;

    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setId(long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

}
