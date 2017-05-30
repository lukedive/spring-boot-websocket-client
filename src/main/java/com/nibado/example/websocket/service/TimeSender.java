package com.nibado.example.websocket.service;

import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class TimeSender {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TimeSender.class);

	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
	@Autowired
	private SimpMessagingTemplate broker;

	@Autowired
	public TimeSender(final SimpMessagingTemplate broker) {
		this.broker = broker;
	}

	@Scheduled(fixedRate = 5000)
	public void run() {
		String time = LocalTime.now().format(TIME_FORMAT);

		log.info("Time broadcast: {}", time);
		broker.convertAndSend("/topic/greetings", new Greeting("Current time is " + time));
	}
	
	@Scheduled(fixedRate = 5000)
	public void runBinary() {
		String time = LocalTime.now().format(TIME_FORMAT);

		log.info("Time broadcast binary: {}", time);
		broker.convertAndSend("/topic/greetings-binary", ("Current time is " + time).getBytes());
	}
	
	
//	@Scheduled(fixedDelay=5000)
//	public void sendMessages(Principal principal) {
//		broker
//	        .convertAndSendToUser(principal.getName(), "/queue/horray", "Horray, " + principal.getName() + "!");
//	}
}
