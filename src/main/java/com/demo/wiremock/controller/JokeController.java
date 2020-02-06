package com.demo.wiremock.controller;

import com.demo.wiremock.service.ChuckNorrisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokeController {

    @Autowired
    private ChuckNorrisService chuckNorrisService;

    @GetMapping("joke/{category}")
    public String getJoke(@PathVariable String category) {
        return chuckNorrisService.getJoke(category);
    }
}
