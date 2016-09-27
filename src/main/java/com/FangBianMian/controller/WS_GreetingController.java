package com.FangBianMian.controller;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WS_GreetingController {    
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;    
    
    /*@MessageMapping("/change-notice")    
    public void greeting(String value){
        this.simpMessagingTemplate.convertAndSend("/topic/notice", value);    
    }*/
    
    @MessageMapping("/change-notice")
    @SendTo("/topic/notice")
    public String greeting(String value) {  
    	System.out.println(value);
        return value;
    }
}