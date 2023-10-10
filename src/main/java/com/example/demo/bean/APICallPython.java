package com.example.demo.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class APICallPython {
    public WebClient localApiGetDemand() {
        return WebClient.create("http://localhost:5000/api");
    }

}
