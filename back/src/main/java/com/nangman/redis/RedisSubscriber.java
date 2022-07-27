package com.nangman.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nangman.db.entity.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            Chat chat = objectMapper.readValue(publishMessage, Chat.class);
            //52에 있던 코드 왜 있는지 이해하면 삭제함
//            if (notice.getType() == NoticeType.MESSAGE) {
//                String roomSubscribeId = notice.getChatRoom().getRoomSubscribeId();
//                messagingTemplate.convertAndSend("/sub/chat/" + roomSubscribeId, notice);
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
