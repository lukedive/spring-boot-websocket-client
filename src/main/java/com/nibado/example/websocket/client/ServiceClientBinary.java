package com.nibado.example.websocket.client;

import java.util.Base64;
import java.util.Scanner;

import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ServiceClientBinary {
    public static void main(String... argv) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new ByteArrayMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://127.0.0.1:8090/hello-binary";
        StompSessionHandler sessionHandler = new MySessionHandlerBinary();
        
        
        String plainCredentials="kowalski:password";
        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());

        final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);

                
        stompClient.connect(url, headers, sessionHandler);

        new Scanner(System.in).nextLine(); //Don't close immediately.
    }
}
