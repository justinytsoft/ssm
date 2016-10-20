package com.FangBianMian.websocket;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.FangBianMian.constant.Common;

public class WebSocketHander implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    /**
     * client第一次连接服务端时执行
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
        // 格式：18380426135 或  admin
        String userName = (String) session.getAttributes().get(Common.WEBSOCKET_USERNAME);
        if(userName!=null && userName.length()==11){
        	sendMessageToUser("admin", new TextMessage(userName));
        }
    }
    
    /**
     * 接受客户端的请求处理
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
    	String msg = (String) webSocketMessage.getPayload(); // msg 数据格式： "用户账号,信息", 如："18380426135,true" 或  "admin,18380426135"
    	String username = msg.split(",")[0];
    	if(username.length()!=11){//给后台发消息
    		sendMessageToUser("admin", new TextMessage(username));
    	}else{ //给用户发消息
    		String flag = msg.split(",")[1];
    		sendMessageToUser(username, new TextMessage(flag));
    	}
    }

    /**
     * 连接出错，关闭连接
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        sendMessageToUsers(new TextMessage(webSocketSession.getAttributes().get(Common.WEBSOCKET_USERNAME) + " 断开了链接"));
        users.remove(webSocketSession);
    }

    /**
     * 连接关闭
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
    	sendMessageToUsers(new TextMessage(webSocketSession.getAttributes().get(Common.WEBSOCKET_USERNAME) + " 断开了链接"));
        users.remove(webSocketSession);
    }
    
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
    /**
     * 给所有在线client发送消息
     *这里的message是client推送给服务端的请求信息，这里假设服务器推 送系统当前时间给client，忽略client传过来的消息，当然你也可以自己处理这个message
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 给某个client发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Common.WEBSOCKET_USERNAME).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}