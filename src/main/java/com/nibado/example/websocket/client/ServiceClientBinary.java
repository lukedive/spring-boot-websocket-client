package com.nibado.example.websocket.client;

import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Base64;
import java.util.Scanner;

public class ServiceClientBinary {
    public static void main(String... argv) {
        try {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new ByteArrayMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://127.0.0.1:8090/ws-binary";
        StompSessionHandler sessionHandler = new MySessionHandlerBinary();
        
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        String auth = "kowalski" + ":" + "password";
        headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(auth.getBytes())));
        

                
        stompClient.connect(url, headers, sessionHandler);
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Scanner(System.in).nextLine(); //Don't close immediately.
    }
}
