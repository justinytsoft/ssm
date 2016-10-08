package com.FangBianMian.websocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.FangBianMian.constant.Common;
import com.FangBianMian.utils.DateUtil;

public class WebSocketHander implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    /**
     * client第一次连接服务端时执行
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
        String userName = (String) session.getAttributes().get(Common.WEBSOCKET_USERNAME);
        if(userName!= null){
        	sendMessageToUsers(new TextMessage(userName + " 加入了讨论"));
        }
    }
    
    /**
     * 接受客户端的请求处理
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<p style='margin-bottom:5px;color:darkgrey;font-size:14px;'>");
    	sb.append(webSocketSession.getAttributes().get(Common.WEBSOCKET_USERNAME));
    	sb.append("(");
    	sb.append(DateUtil.formatDateTime(new Date()));
    	sb.append(")");
    	sb.append("</p>");
    	sb.append(webSocketMessage.getPayload());
    	sendMessageToUsers(new TextMessage(sb.toString()));
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