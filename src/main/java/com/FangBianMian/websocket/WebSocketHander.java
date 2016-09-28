package com.FangBianMian.websocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WebSocketHander implements WebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(WebSocketHander.class);
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    //client第一次连接服务端时执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("连接成功......");
        users.add(session);
        String userName = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        logger.debug("userName="+userName);
        if(userName!= null){
            int count = 5;
            session.sendMessage(new TextMessage(count + ""));
        }
    }
    //接受客户端的请求处理，这里可以指定方法是给所以在线的client推送消息还是给某个特定的client推送(区别:方法的参数列表)
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + ""));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.debug("连接出错，关闭连接......");
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.debug("连接关闭......" + closeStatus.toString());
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    /**
     * 给所有在线client发送消息
     *这里的message是client推送给服务端的请求信息，这里假设服务器推     送系统当前时间给client，忽略client传过来的消息，当然你也可以自己处理这个message
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    System.out.println("msg="+getTime());
                    user.sendMessage(getTime());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取系统当前时间并转换为TextMessage
     * @return
     */
public TextMessage getTime(){
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time =  format.format(date);
     CharBuffer temp=CharBuffer.wrap("系统当前时间："+time);
     TextMessage msg = new TextMessage(temp);
    return msg;
}

    /**
     * 给某个client发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(getTime());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}