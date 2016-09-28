package com.FangBianMian.websocket;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket//开启websocket,使用注解，在Spring boot的run()之后会加载websocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHander(),"/veg").addInterceptors(new HandshakeInterceptor()); //支持websocket 的访问链接
        registry.addHandler(new WebSocketHander(),"/com/veg").addInterceptors(new HandshakeInterceptor()).withSockJS(); //不支持websocket的访问链接
    }
}