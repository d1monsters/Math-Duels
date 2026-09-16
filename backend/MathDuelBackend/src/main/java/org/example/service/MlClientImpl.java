package org.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class MlClientImpl implements MlClient {

    private final RestTemplate restTemplate;
    private final String mlServiceUrl;

    public MlClientImpl(
            RestTemplate restTemplate,
            @Value("${ml.service.url}") String mlServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.mlServiceUrl = mlServiceUrl;
    }

    @Override
    public Map validateAnswer(String question, String correctAnswer, String userAnswer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "question", question,
                "correct_answer", correctAnswer,
                "user_answer", userAnswer
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(
                mlServiceUrl + "/api/validate",
                request,
                Map.class
        );
    }
}
