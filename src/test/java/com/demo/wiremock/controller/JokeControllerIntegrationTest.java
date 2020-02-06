package com.demo.wiremock.controller;

import com.demo.wiremock.Application;
import com.demo.wiremock.util.WiremockConfig;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {Application.class, JokeControllerIntegrationTest.Config.class})
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class JokeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WiremockConfig wiremockConfig;

    @Before
    public void setUp() {

    }

    @Test
    public void testGetJoke() throws Exception {
        String category = "myTestCategory";

        wiremockConfig.getWireMock().stubFor(WireMock.get(urlPathEqualTo("/jokes/random"))
                .withQueryParam("category", equalTo(category))
                .willReturn(okJson("{ \"value\":\"Some Chuck joke\"}")));

        mockMvc.perform(get("/joke/" + category)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Some Chuck joke")));
    }

    public static class Config {
        @Autowired
        private WiremockConfig wiremockConfig;

        @PostConstruct
        public void setupInitialStub() {
            wiremockConfig.getWireMock().stubFor(WireMock.get(urlPathEqualTo("/jokes/random"))
                    .withQueryParam("category", equalTo("animal"))
                    .willReturn(okJson("{ \"value\":\"Initial joke\"}")));
        }
    }



}