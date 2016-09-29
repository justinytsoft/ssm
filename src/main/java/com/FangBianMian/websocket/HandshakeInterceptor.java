package com.FangBianMian.websocket;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

import com.FangBianMian.constant.Common;

import javax.servlet.http.HttpSession;

import java.util.Map;

public class HandshakeInterceptor implements org.springframework.web.socket.server.HandshakeInterceptor {

    //进入hander之前的拦截
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, 
    								WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //获取客户端的名称，以便可以定向的向某个客户端推送消息
                String userName = (String) session.getAttribute(Common.WEBSOCKET_USERNAME);
                map.put(Common.WEBSOCKET_USERNAME,userName);
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
    						WebSocketHandler webSocketHandler, Exception e) {

    }
}