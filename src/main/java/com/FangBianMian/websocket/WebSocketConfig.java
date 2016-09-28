package com.FangBianMian.websocket;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println("注册");
		//portfolio-stomp就是websocket的端点，客户端需要注册这个端点进行链接，withSockJS允许客户端利用sockjs进行浏览器兼容性处理
		registry.addEndpoint("/portfolio-stomp").withSockJS();
	}   

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		System.out.println("启动");
		registry.enableSimpleBroker("/topic");               //设置服务器广播消息的基础路径
		registry.setApplicationDestinationPrefixes("/app");  //设置客户端发送消息的基础路径
	}
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration arg0) {
		// TODO Auto-generated method stub
		
	}
}