package com.santacarolina.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.User;
import com.santacarolina.util.ApiRequest;

public class UserDAO {
    
    private Logger logger = LogManager.getLogger();
    private final ApiRequest<User> apiRequest = new ApiRequest<>(User.class);

    public Optional<User> findByUsername(String username) throws FetchFailException {
        String query = "user/username/" + username;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

}
