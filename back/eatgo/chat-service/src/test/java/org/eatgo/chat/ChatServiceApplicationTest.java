package org.eatgo.chat;

import org.eatgo.chat.service.ChatService;
import org.eatgo.common.domain.query.ChatMessageQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@SpringBootTest
public class ChatServiceApplicationTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test1(){
        chatService.insertChatHistory(new ChatMessageQuery(15,1,"ksdjflk"));
    }

    @Test
    public void test2(){
        List<ChatMessageQuery> list = chatService.history(new ChatMessageQuery(15, 1, "ksdjflk"));

        list.forEach(System.out::println);
    }
}
