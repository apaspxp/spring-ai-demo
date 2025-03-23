package com.pxp.spring_ai_demo.controller;

import com.pxp.spring_ai_demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Bean
    public RouterFunction<ServerResponse> endpoints() {
        return RouterFunctions.route()
                .GET("/chat/{query}", request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(chatService.chat(request.pathVariable("query")))
                )
                .GET("/chat1/{query}", request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(chatService.chat1(request.pathVariable("query")))
                )
                .GET("/ask/{query}", request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(chatService.ask(request.pathVariable("query")))
                )
                .build();
    }
}
