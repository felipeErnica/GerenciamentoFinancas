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

    private static final Logger logger = LogManager.getLogger(ApiRequest.class);

    private final ObjectMapper mapper;
    private final String URL_BACKEND = "http://localhost:5005";
    private final HttpClient client = HttpClient.newBuilder().build();
    private HttpResponse<String> response;
    private HttpRequest request;
    private Class<T> tClass;

    public ApiRequest(Class<T> tClass) {
        this.tClass = tClass;
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public Optional<T> getRequest(String queryString) throws URISyntaxException, IOException, InterruptedException {
        logger.info("Enviando query: " + URL_BACKEND + queryString);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 404)
            throw new InterruptedException("Server Error: " + response.statusCode());
        String json = response.body();
        logger.info("Response: " + response.statusCode());
        if (json.isEmpty())
            return Optional.empty();
        else
            return Optional.ofNullable(mapper.readValue(json, tClass));
    }

    public List<T> getListRequest(String queryString) throws URISyntaxException, IOException, InterruptedException {
        logger.info("Enviando query: " + URL_BACKEND + queryString);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 404)
            throw new InterruptedException("Server Error: " + response.statusCode());
        String json = response.body();
        logger.info("Response: " + response.statusCode());
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }

    public void postRequest(String queryString, T t) throws IOException, URISyntaxException, InterruptedException {
        String json = mapper.writeValueAsString(t);
        logger.info("Enviando Objeto: " + json);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response POST: " + response.statusCode());
    }

    public T postRequestWithResponse(String queryString, T t)
            throws InterruptedException, IOException, URISyntaxException {
        String json = mapper.writeValueAsString(t);
        logger.info("Enviando Objeto: " + json);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response POST: " + response.statusCode());
        if (response.statusCode() != 200)
            throw new InterruptedException("Server Error: " + response.statusCode());
        return mapper.readValue(response.body(), tClass);
    }

    public void postListRequest(String queryString, List<T> list)
            throws IOException, URISyntaxException, InterruptedException {
        String json = mapper.writeValueAsString(list);
        logger.info("Enviando Batch: " + json);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response Batch: " + response.statusCode());
        if (response.statusCode() != 200)
            throw new InterruptedException("Server Error: " + response.statusCode());
    }

    public void deleteRequest(String query) throws URISyntaxException, IOException, InterruptedException {
        logger.info("Delete Request: " + URL_BACKEND + query);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + query))
                .DELETE()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response Delete: " + response.statusCode());
        if (response.statusCode() != 200)
            throw new InterruptedException("Server Error: " + response.statusCode());
    }

}
