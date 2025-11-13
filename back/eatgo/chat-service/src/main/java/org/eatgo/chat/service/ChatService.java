package org.eatgo.chat.service;

import org.eatgo.common.domain.query.ChatMessageQuery;

import java.util.List;

public interface ChatService {
    public void insertChatHistory(ChatMessageQuery chatMessageQuery);

    public List<ChatMessageQuery> history(ChatMessageQuery chatMessageQuery);
}
