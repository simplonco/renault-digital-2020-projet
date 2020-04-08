package com.renault.integration;

import com.renault.CarsApplication;
import com.renault.services.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.server.ResponseStatusException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.valueOf;

@SpringBootTest(classes = CarsApplication.class)
public abstract class AbstractIntegration {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EntityManager entityManager;

    @LocalServerPort
    int port;

    @BeforeEach
    public void beforeEach() {
        applicationService.deleteAll();
        applicationService.insertData();
    }

    JsonReader get(String path) {
        String query = format("http://localhost:%s/%s", port, path);
        HttpClient client = newHttpClient();
        HttpRequest request = newBuilder().uri(URI.create(query)).build();
        try {
            HttpResponse<String> response = client.send(request, ofString());
            if (response.statusCode() != 200) {
                throw new ResponseStatusException(valueOf(response.statusCode()),
                        format("Wrong status code: %d", response.statusCode()));
            }
            String json = response.body();
            json = json.isBlank() ? "(blank)" : json;
            System.out.println(format("Response from '%s' -> %s", query, json));
            return Json.createReader(new StringReader(json));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void post(String path, JsonObject body) {
        String query = format("http://localhost:%s/%s", port, path);
        HttpClient client = newHttpClient();
        HttpRequest request = newBuilder()
                .uri(URI.create(query))
                .POST(ofString(body == null ? "" : body.toString()))
                .header("Content-Type", "application/json; charset=utf8")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .build();
        try {
            HttpResponse<String> response = client.send(request, ofString());
            if (response.statusCode() != 200) {
                throw new ResponseStatusException(valueOf(response.statusCode()),
                        format("Wrong status code: %d", response.statusCode()));
            }
            String json = response.body();
            json = json.isBlank() ? "(blank)" : json;
            System.out.println(format("Response from '%s' -> %s", query, json));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void delete(String path) {
        String query = format("http://localhost:%s/%s", port, path);
        HttpClient client = newHttpClient();
        HttpRequest request = newBuilder()
                .uri(URI.create(query))
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .DELETE().build();
        try {
            HttpResponse<String> response = client.send(request, ofString());
            if (response.statusCode() != 200) {
                throw new ResponseStatusException(valueOf(response.statusCode()),
                        format("Wrong status code: %d", response.statusCode()));
            }
            String json = response.body();
            json = json.isBlank() ? "(blank)" : json;
            System.out.println(format("Response from '%s' -> %s", query, json));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    Set<String> getBrands() {
        return new HashSet<>(getSelectColumnsAsStrings("SELECT brand FROM cars"));
    }

    List<String> getCarModelsForBrand(String brand) {
        return getSelectColumnsAsStrings(format("SELECT model FROM cars WHERE brand = '%s'", brand));
    }

    List<Integer> getCarIdsForBrand(String brand) {
        return getSelectColumnsAsInts(format("SELECT id FROM cars WHERE brand = '%s'", brand));
    }

    private List<Integer> getSelectColumnsAsInts(String sql) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<Object> resultList = nativeQuery.getResultList();
        return resultList.stream().map(object -> (Integer) object).collect(toList());
    }

    private List<String> getSelectColumnsAsStrings(String sql) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<Object> resultList = nativeQuery.getResultList();
        return resultList.stream().map(object -> (String) object).collect(toList());
    }

}
