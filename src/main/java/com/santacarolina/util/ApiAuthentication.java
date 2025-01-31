package com.santacarolina.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santacarolina.exceptions.AuthenticationException;
import com.santacarolina.model.AuthToken;
import com.santacarolina.model.User;

public class ApiAuthentication {

    private static final Logger logger = LogManager.getLogger(ApiRequest.class);

    private final HttpClient client = HttpClient.newBuilder().build();
    private HttpResponse<String> response;
    private ObjectMapper mapper;

    public ApiAuthentication() {
        this.mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public Optional<AuthToken> loginUser(User user) throws URISyntaxException, IOException, InterruptedException, AuthenticationException {
        String userJson = mapper.writeValueAsString(user);

        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(userJson);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(ApiRequest.URL_BACKEND + "/user/login"))
            .header("Content-Type", "application/json")
            .POST(bodyPublisher)
            .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String tokenJson = response.body();

        int statusCode = response.statusCode();
        logger.info("Authentication Response: " + statusCode + "\n");

        if (statusCode == 403) throw new AuthenticationException();

        if (StringUtils.isBlank(tokenJson)) {
            return Optional.empty();
        } else {
            AuthToken authToken = mapper.readValue(tokenJson, AuthToken.class);
            return Optional.ofNullable(authToken);
        }
    }


}
