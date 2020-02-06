package com.demo.wiremock.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WiremockConfig {

    @Value("${wiremock.port}")
    private Integer port;

    private WireMockServer wireMock;

    @PostConstruct
    public void setupWiremock() {
        wireMock = new WireMockServer(port);
        wireMock.start();
    }

    public WireMockServer getWireMock() {
        return wireMock;
    }
}
