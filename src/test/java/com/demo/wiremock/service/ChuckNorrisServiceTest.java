package com.demo.wiremock.service;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;


public class ChuckNorrisServiceTest {

    private ChuckNorrisService chuckNorrisService = new ChuckNorrisService();

    @Rule
    public WireMockRule wireMock = new WireMockRule(Options.DYNAMIC_PORT);

    @Before
    public void setup() {
        Field jokesUrl = ReflectionUtils.findField(ChuckNorrisService.class, "jokesUrl");
        jokesUrl.setAccessible(true);
        ReflectionUtils.setField(jokesUrl, chuckNorrisService, "http://localhost:" + wireMock.port() + "/jokes/random");

        wireMock.stubFor(get(urlPathEqualTo("/jokes/random"))
                .withQueryParam("category", equalTo("animal"))
                .willReturn(okJson("{ \"value\":\"test joke\"}")));

        chuckNorrisService.setupWebClient(null);
    }

    @Test
    public void testGetJoke() {
        assertEquals("test joke", chuckNorrisService.getJoke("animal"));
    }
}