package com.demo.wiremock.service;

import com.demo.wiremock.dto.Joke;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChuckNorrisService {

    @Value("${chuck.base-url}/jokes/random")
    private String jokesUrl;

    private WebClient webClient;

    @EventListener
    public void setupWebClient(ContextRefreshedEvent contextRefreshedEvent) {
        webClient = WebClient.builder()
                .baseUrl(jokesUrl)
                .build();

        Objects.requireNonNull(getJoke("animal"), "Unable to get response from API");
    }

    public String getJoke(String category) {
        Joke joke = webClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("category", category)
                .build())
                .retrieve()
                .bodyToMono(Joke.class)
                .onErrorReturn(new Joke())
                .block();
        return joke.getValue();
    }

}
