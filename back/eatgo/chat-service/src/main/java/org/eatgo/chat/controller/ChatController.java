package org.eatgo.chat.controller;

import org.eatgo.chat.service.ChatService;
import org.eatgo.common.domain.query.ChatMessageQuery;
import org.eatgo.common.domain.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/history/list")
    public ResultVo<List<ChatMessageQuery>> chatHistory(@RequestBody ChatMessageQuery query) {

        List<ChatMessageQuery> history = chatService.history(query);
        return ResultVo.success("聊天记录列表", history);
    }
}
