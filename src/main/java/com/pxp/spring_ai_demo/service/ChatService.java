package com.pxp.spring_ai_demo.service;

import com.pxp.spring_ai_demo.model.DateTimeModel;
import com.pxp.spring_ai_demo.model.InternetAccessRequest;
import com.pxp.spring_ai_demo.tools.CurrentDateAndTimeTool;
import com.pxp.spring_ai_demo.tools.InternetAccessTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .defaultSystem("""
                        You are a friendly chatbot and your name is Danny. You are here to answer user questions in a very friendly way. 
                        Check for tools only when they are relevant to user questions.
                        Pass correct input to the tools.
                        If you don't know the answer to a question, you can say "I don't know". 
                        If the information you have is outdated and you have no realtime information then say so. 
                        If you want to end the conversation, you can say "Goodbye". Have fun chatting with users!
                        """)
                .build();
    }

    ToolCallback getCurrentDateAndTime = FunctionToolCallback
            .builder("getCurrentDateAndTime", CurrentDateAndTimeTool::new)
            .description("Get the current date and time. This tool receives a string in format yyyy-MM-dd HH:mm:ss")
            .inputType(DateTimeModel.class)
            .build();

    ToolCallback getCurrentGoldPrice = FunctionToolCallback
            .builder("getCurrentGoldPrice", InternetAccessTool::new)
            .description("Information for coding questions")
            .inputType(InternetAccessRequest.class)
            .build();

    public Flux<String> chat(String query) {
        return chatClient.prompt()
                .user(query)
                .tools(getCurrentDateAndTime)
                .stream()
                .content();
    }

    public Flux<String> chat1(String query) {
        return chatClient.prompt()
                .user(query)
//                .tools(getCurrentDateAndTime)
                .stream()
                .content();
    }

    public Flux<String> ask(String query) {
        return chatClient.prompt()
                .system("""
                        Carefully reread the user's question, thoroughly scan the available information, and select only the most relevant details to craft a precise and meaningful response.
                        """)
                .user(query)
                .tools(getCurrentGoldPrice)
                .stream()
                .content();
    }
}
