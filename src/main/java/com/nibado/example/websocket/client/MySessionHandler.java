package com.nibado.example.websocket.client;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.nibado.example.websocket.service.Greeting;

public class MySessionHandler extends StompSessionHandlerAdapter {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MySessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/greetings", this);
        session.send("/app/hello", "{\"name\":\"Client\"}".getBytes());

        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
            Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        if (payload instanceof Greeting) {
            log.info("Received greeting: {}", ((Greeting) payload).getContent());
        } else {
            log.info("Received: {}", payload);
        }
    }
}
