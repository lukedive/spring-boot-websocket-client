package com.nibado.example.websocket.service;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GreetingController.class);
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        log.info("Received hello: {}", message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }
    
    @MessageMapping("/hello-binary")
    @SendTo("/topic/greetings-binary")
    public byte[] greetingBinary(byte[] message) throws Exception {
        String msg = new String(message, "UTF-8");
		log.info("Received hello: {}", msg);
        return ("Hello, " + msg + "!").getBytes();
    }
}
