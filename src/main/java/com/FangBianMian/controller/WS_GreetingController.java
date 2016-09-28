package com.FangBianMian.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WS_GreetingController {    
    
    @MessageMapping("/handler")
    @SendTo("/topic/greetings")
    public String greeting(String value) throws InterruptedException {  
    	System.out.println(value);
    	Thread.sleep(3000); // simulated delay
        return "handle";
    }
}