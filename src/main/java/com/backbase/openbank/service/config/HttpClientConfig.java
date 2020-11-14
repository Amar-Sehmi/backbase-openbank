package com.backbase.openbank.service.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@SuppressWarnings("unused")
public class HttpClientConfig {

    @Autowired
    Environment env;

    @Bean
    public HttpClient httpClient() {
        return HttpClients.createDefault();
    }

    @Bean
    public HttpGet httpGet() {
        return new HttpGet(env.getRequiredProperty("backbase.openbank.url"));
    }
}
