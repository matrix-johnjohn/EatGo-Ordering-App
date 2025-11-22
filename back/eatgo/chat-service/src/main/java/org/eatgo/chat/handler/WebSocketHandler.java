package org.eatgo.chat.handler;

import cn.hutool.json.JSONUtil;
import org.eatgo.chat.service.ChatService;
import org.eatgo.common.domain.query.ChatMessageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // 用户信息存储
    private static final Map<Integer, WebSocketSession>USER_SESSIONS=new ConcurrentHashMap<>();

    @Autowired
    private ChatService chatService;

    //用户连接处理
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //获取请求参数
        String query=Objects.requireNonNull(session.getUri()).getQuery();
        //参数中获取user当前发送请求的用户id
        String userId=query.split("=")[1];
        //存入映射
        USER_SESSIONS.put(Integer.parseInt(userId), session);
        session.sendMessage(new TextMessage("欢迎连接 WebSocket 服务器！"));
    }

    //用户信息发送
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //获取用户发送消息
        ChatMessageQuery payload=JSONUtil.toBean(message.getPayload(), ChatMessageQuery.class);

        //数据写入数据库
        chatService.insertChatHistory(payload);

        //获取转发目标
        WebSocketSession target=USER_SESSIONS.get(payload.getTo());

        //转发
        if(!JSONUtil.isNull(target)&&target.isOpen()){
            target.sendMessage(new TextMessage(JSONUtil.toJsonPrettyStr(payload)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //获取请求参数
        String query=Objects.requireNonNull(session.getUri()).getQuery();
        //参数中获取user当前发送请求的用户id
        Integer userId=Integer.parseInt(query.split("=")[1]);
        //删除映射
        USER_SESSIONS.remove(userId);
    }
}