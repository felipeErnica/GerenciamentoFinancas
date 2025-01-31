package com.santacarolina.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiRequest<T> {

    private static String authenticationToken;

    private static final Logger logger = LogManager.getLogger(ApiRequest.class);

    private final ObjectMapper mapper;
    private final String URL_BACKEND = "http://localhost:5005";
    private final HttpClient client = HttpClient.newBuilder().build();
    private HttpResponse<String> response;
    private HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().header("Authorization", "Bearer " + authenticationToken);
    private Class<T> tClass;

    public ApiRequest(Class<T> tClass) {
        this.tClass = tClass;
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public Optional<T> getRequest(String queryString) throws URISyntaxException, IOException, InterruptedException {
        logger.info("Enviando query: " + URL_BACKEND + queryString + "\n");
        HttpRequest request = requestBuilder.uri(new URI(URL_BACKEND + queryString))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 404)
            throw new InterruptedException("Server Error: " + response.statusCode());
        String json = response.body();
        logger.info("Response: " + response.statusCode() + "\n");
        if (json.isEmpty())
            return Optional.empty();
        else
            return Optional.ofNullable(mapper.readValue(json, tClass));
    }

    public List<T> getListRequest(String queryString) throws URISyntaxException, IOException, InterruptedException {
        logger.info("Enviando query: " + URL_BACKEND + queryString + "\n");
        HttpRequest request = requestBuilder.uri(new URI(URL_BACKEND + queryString))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 404) throw new InterruptedException("Server Error: " + response.statusCode());
        String json = response.body();
        logger.info("Response: " + response.statusCode() + "\n");
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }

    public void postRequest(String queryString, T t) throws IOException, URISyntaxException, InterruptedException {
        String json = mapper.writeValueAsString(t);
        logger.info("Enviando query: " + URL_BACKEND + queryString + "\n");
        logger.info("Enviando Objeto: " + json + "\n");
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = requestBuilder.uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response POST: " + response.statusCode() + "\n");
    }

    public T postRequestWithResponse(String queryString, T t)
            throws InterruptedException, IOException, URISyntaxException {
        String json = mapper.writeValueAsString(t);
        logger.info("Enviando query: " + URL_BACKEND + queryString + "\n");
        logger.info("Enviando Objeto: " + json + "\n");
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = requestBuilder.uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response POST: " + response.statusCode() + "\n");
        if (response.statusCode() != 200)
            throw new InterruptedException("Server Error: " + response.statusCode());
        return mapper.readValue(response.body(), tClass);
    }

    public void postListRequest(String queryString, List<T> list)
            throws IOException, URISyntaxException, InterruptedException {
        String json = mapper.writeValueAsString(list);
        logger.info("Enviando query: " + URL_BACKEND + queryString + "\n");
        logger.info("Enviando Batch: " + json + "\n");
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = requestBuilder.uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response POST: " + response.statusCode() + "\n");
        if (response.statusCode() != 200)
            throw new InterruptedException("Server Error: " + response.statusCode());
    }

    public void deleteRequest(String query) throws URISyntaxException, IOException, InterruptedException {
        logger.info("Enviando query: " + URL_BACKEND + query + "\n");
        HttpRequest request = requestBuilder.uri(new URI(URL_BACKEND + query))
                .DELETE()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response Delete: " + response.statusCode() + "\n");
        if (response.statusCode() != 200)
            throw new InterruptedException("Server Error: " + response.statusCode());
    }

    public static String getAuthenticationToken() { return authenticationToken; }
    public static void setAuthenticationToken(String authenticationToken) { ApiRequest.authenticationToken = authenticationToken; }
}
