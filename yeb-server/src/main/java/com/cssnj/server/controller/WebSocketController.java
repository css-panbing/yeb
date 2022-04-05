package com.cssnj.server.controller;

import com.cssnj.server.pojo.Admin;
import com.cssnj.server.pojo.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

/**
 * webSocket
 *
 * @author panbing
 * @date 2022/4/5 11:51
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 发送消息
     * @param authentication
     * @param chatMessage
     */
    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMessage chatMessage){
        Admin admin = (Admin) authentication.getPrincipal();
        chatMessage.setFrom(admin.getUsername());
        chatMessage.setFromNickName(admin.getName());
        chatMessage.setSendTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getTo(), "/queue/chat", chatMessage);
    }


}
