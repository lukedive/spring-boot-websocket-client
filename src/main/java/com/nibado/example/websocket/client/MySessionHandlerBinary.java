package com.nibado.example.websocket.client;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class MySessionHandlerBinary extends StompSessionHandlerAdapter {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MySessionHandlerBinary.class);

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/topic/greetings-binary", this);
		session.subscribe("/user/queue/horray", this);
		session.send("/app/hello-binary", "Sie ma".getBytes());

		log.info("New session: {}", session.getSessionId());
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		exception.printStackTrace();
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return byte[].class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		log.info("Received: {}", new String ((byte[]) payload));
		
	}
}
