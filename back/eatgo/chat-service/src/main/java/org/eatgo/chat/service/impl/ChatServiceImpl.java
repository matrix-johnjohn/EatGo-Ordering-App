package org.eatgo.chat.service.impl;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.eatgo.chat.mapper.ChatMapper;
import org.eatgo.chat.service.ChatService;
import org.eatgo.common.domain.query.ChatMessageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void insertChatHistory(ChatMessageQuery chatMessageQuery) {
        // 数据库写入数据
        chatMapper.insertChatHistory(chatMessageQuery);
        // 数据库缓存写入数据
        Jedis jedis=jedisPool.getResource();
        // 生成聊天室id
        String roomId=GenerateRoomId(chatMessageQuery.getTo(), chatMessageQuery.getFrom());
        // 生成key
        String key=String.format("chat:history:%s", roomId);
        // 写入数据
        jedis.rpush(key, JSONUtil.toJsonStr(chatMessageQuery));

        jedis.close();
    }

    @Override
    public List<ChatMessageQuery> history(ChatMessageQuery chatMessageQuery) {
        Jedis jedis=jedisPool.getResource();

        // 返回数据
        ArrayList<ChatMessageQuery>result=new ArrayList<>();
        // 生成聊天室id
        String roomId=GenerateRoomId(chatMessageQuery.getTo(), chatMessageQuery.getFrom());

        // 获得列表
        List<String>list=jedis.lrange(String.format("chat:history:%s", roomId), 0, -1);

        for(String s : list) {
            ChatMessageQuery q=JSONUtil.toBean(s, ChatMessageQuery.class);
            result.add(q);
        }
        // 返回列表
        return result;
    }

    //生成聊天室id
    public String GenerateRoomId(Integer ...ids){
        int min=Math.min(ids[0], ids[1]);
        int max=Math.max(ids[0], ids[1]);
        return String.format("%d_%d", min, max);
    }
}
