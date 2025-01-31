package com.santacarolina.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.santacarolina.exceptions.AuthenticationException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.AuthToken;
import com.santacarolina.model.User;
import com.santacarolina.util.ApiAuthentication;

public class UserDAO {

    private Logger logger = LogManager.getLogger();
    private final ApiAuthentication authentication = new ApiAuthentication();
    
    public Optional<AuthToken> login(User user) throws FetchFailException, AuthenticationException {
        try {
            return authentication.loginUser(user);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    }

    public void registerUser(User user) throws SaveFailException {
        try {
            authentication.registerUser(user);
        } catch (IOException | InterruptedException | URISyntaxException | AuthenticationException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public Optional<User> findByUsername(String username) throws FetchFailException {
        try {
            return authentication.getRequest(username);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

}
