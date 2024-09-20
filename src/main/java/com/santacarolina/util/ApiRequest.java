package com.santacarolina.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class ApiRequest<T> {

    private final Logger logger = LogManager.getLogger();
    private final ObjectMapper mapper;
    private final String URL_BACKEND = "http://localhost:8080";
    private final HttpClient client = HttpClient.newBuilder().build();
    private HttpResponse<String> response;
    private HttpRequest request;

    public ApiRequest() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public Optional<T> getRequest(String queryString, Class<T> tClass) throws URISyntaxException, IOException, InterruptedException {
        logger.debug("Enviando query: " + queryString);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) throw new InterruptedException("Server Error: " + response.statusCode());
        String json = response.body();
        logger.debug("JSON: " + json);
        if (json.isEmpty()) return Optional.empty();
        else return Optional.ofNullable(mapper.readValue(json, tClass));
    }

    public List<T> getListRequest(String queryString, Class<T> tClass) throws URISyntaxException, IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new InterruptedException("Server Error: " + response.statusCode());

        String json = response.body();
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }

    public void postRequest(String queryString, T t) throws IOException, URISyntaxException, InterruptedException {
        String json = mapper.writeValueAsString(t);
        logger.debug("JSON Body: " + json);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new InterruptedException("Server Error: " + response.statusCode());
    }

    public void postListRequest(String queryString, List<T> list) throws IOException, URISyntaxException, InterruptedException {
        String json = mapper.writeValueAsString(list);
        logger.debug("JSON Body: " + json);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + queryString))
                .header("Content-Type", "application/json")
                .POST(bodyPublisher)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new InterruptedException("Server Error: " + response.statusCode());
    }

    public void deleteRequest(String query) throws URISyntaxException, IOException, InterruptedException {
        logger.debug("Delete Request: " + query);
        request = HttpRequest.newBuilder()
                .uri(new URI(URL_BACKEND + query))
                .DELETE()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.debug("Response: " + response.statusCode());
        if (response.statusCode() != 200) throw new InterruptedException("Server Error: " + response.statusCode());
    }
}
