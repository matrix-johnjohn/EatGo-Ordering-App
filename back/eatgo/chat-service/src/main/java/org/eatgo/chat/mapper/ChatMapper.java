package org.eatgo.chat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.eatgo.common.domain.query.ChatMessageQuery;

@Mapper
public interface ChatMapper {

    @Insert("insert into chat (`from`,`to`,`message`) values (#{from},#{to},#{message})")
    public void insertChatHistory(ChatMessageQuery chatMessageQuery);
}
