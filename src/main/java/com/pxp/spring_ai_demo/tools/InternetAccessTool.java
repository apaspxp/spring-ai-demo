package com.pxp.spring_ai_demo.tools;

import com.pxp.spring_ai_demo.model.InternetAccessRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InternetAccessTool {

    private InternetAccessRequest internetAccessRequest;

    public InternetAccessTool(InternetAccessRequest internetAccessRequest) {
        this.internetAccessRequest = internetAccessRequest;
    }

    public String getCurrentGoldPrice() {
        System.out.println("*****************************InternetAccessTool.search " + internetAccessRequest.query());
        var restClient = RestClient.builder()
                .baseUrl("https://www.googleapis.com/customsearch/v1")
                .build();
        var apiKey = "";
        var cseId = "";
        var uri = """
                ?key=%s&cx=%s&q=%s
                """.formatted(apiKey, cseId, Objects.isNull(internetAccessRequest.query()) || internetAccessRequest.query().isEmpty() ? "Current news headlines from India" : internetAccessRequest.query());
        var response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(Map.class);
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

        return items.stream()
                .limit(2)
                .map(item -> {
                            try {
                                Document doc = Jsoup.connect((String) item.get("link")).get();
                                var text = doc.text();  // Extracts readable text only (without HTML)
                                System.out.println("*****************************text " + text);
                                return text;
                            } catch (IOException e) {
                                return "Error fetching content: " + e.getMessage();
                            }
                        }
                )
                .collect(Collectors.joining(""));
    }

}
