package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationConfigLogger {

    @Value("${spring.application.name:N/A}")
    private String applicationName;

    @Value("${app.environment:N/A}")
    private String environment;

    @PostConstruct
    private void postConstruct() {
        log.info("applicationName={} environment={}",
                applicationName, environment);
    }
}