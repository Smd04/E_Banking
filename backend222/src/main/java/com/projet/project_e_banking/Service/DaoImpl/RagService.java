package com.projet.project_e_banking.Service.DaoImpl;

// Java example to call RAG service from Spring

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RagService {
    private static final String RAG_API_URL = "http://localhost:5005/ask";

    public String askRag(String question, List<Map<String, String>> chatHistory) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> body = new HashMap<>();
        body.put("query", question);
        body.put("history", chatHistory);

        try {
            String requestBody = mapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(RAG_API_URL))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode json = mapper.readTree(response.body());
            return json.get("response").asText();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();  // You can log this
            return "Error calling RAG service: " + e.getMessage();
        }
    }

}

