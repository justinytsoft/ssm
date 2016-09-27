package com.FangBianMian.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WS_GreetingController {    
    
   /* @MessageMapping("/websocket")    
    public void greeting(String value){
    	System.out.println(value);
        this.simpMessagingTemplate.convertAndSend("/topic/notice", value);    
    }*/
    
    @MessageMapping("/websocket")
    @SendTo("/topic/notice")
    public String greeting(String value) {  
    	System.out.println(value);
        return value;
    }
}