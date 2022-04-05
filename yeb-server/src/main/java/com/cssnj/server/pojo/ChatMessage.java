package com.cssnj.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 聊天消息类
 *
 * @author panbing
 * @date 2022/4/5 11:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)//开启链式编程
public class ChatMessage {

    private String from;//发送者
    private String to;//接收者
    private String content;//发送内容
    private LocalDateTime sendTime;//发送时间
    private String fromNickName;//发送者昵称

}
