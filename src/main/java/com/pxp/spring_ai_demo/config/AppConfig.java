package com.pxp.spring_ai_demo.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public TomcatConnectorCustomizer connectorCustomizer() {
        return connector -> connector.setAsyncTimeout(0);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://www.googleapis.com/customsearch/v1")
                .build();
    }
}
