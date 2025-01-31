package com.santacarolina.areas.user.register;

import com.santacarolina.model.User;

public class FormModel {

    private User user = new User();
    private String username;
    private String password;
    private String confirmPassword;

    public User getUser() { return user; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }

    public void setUsername(String username) {
        user.setUsername(username);
        this.username = username; 
    }

    public void setPassword(String password) { 
        user.setPassword(password);
        this.password = password; 
    }
    
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

}
