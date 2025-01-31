package com.santacarolina.areas.user.login;

import com.santacarolina.model.User;

public class FormModel {

    private User user = new User();
    private String username;
    private String password;

    public User getUser() { return user; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setUsername(String username) {
        user.setUsername(username);
        this.username = username; 
    }

    public void setPassword(String password) { 
        user.setPassword(password);
        this.password = password; 
    }
    
}
