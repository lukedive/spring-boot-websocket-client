package com.nibado.example.websocket.client;

import java.util.Base64;
import java.util.Scanner;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ServiceClient {
    public static void main(String... argv) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://127.0.0.1:8090/ws";
        
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        String auth = "user" + ":" + "password";
        headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(auth.getBytes())));
        
        StompSessionHandler sessionHandler = new MySessionHandler();
        
        ListenableFuture<StompSession> lf = stompClient.connect(url, headers, sessionHandler);
        
        lf.addCallback(new ListenableFutureCallback<StompSession>() {

            @Override
            public void onSuccess(StompSession result) {
                System.out.println(result);
                
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex);
                ex.printStackTrace();
                
            }
        });
     
        

        new Scanner(System.in).nextLine(); //Don't close immediately.
    }
}
