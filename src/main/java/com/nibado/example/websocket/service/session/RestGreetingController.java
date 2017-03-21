package com.nibado.example.websocket.service.session;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nibado.example.websocket.service.Greeting;

@RestController
public class RestGreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        
        //way to get login 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedName = auth.getName(); //get logged in username

        
        return new Greeting(counter.incrementAndGet() + " " + loggedName + " " +
                            String.format(template, name));
    }
}
