package com.demo.wiremock.service;

import org.springframework.beans.factory.annotation.Value;

public class ChuckNorrisService {

    @Value("${chuck.base-url}/jokes/random")
    private String jokesUrl;

    public String getJoke(String category) {
        return null;
    }

}
