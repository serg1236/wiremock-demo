package com.demo.wiremock.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.SocketUtils;

import java.util.Properties;

public class WiremockEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties properties = new Properties();
        properties.setProperty("wiremock.port", String.valueOf(SocketUtils.findAvailableTcpPort()));
        PropertiesPropertySource propertySource = new PropertiesPropertySource("wiremock", properties);
        environment.getPropertySources().addLast(propertySource);
    }
}
