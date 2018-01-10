package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageLoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApp.class);

    private final RedisTemplate<TextMessage, Integer> redisTemplate;

    public MessageLoggingService(final RedisTemplate<TextMessage, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @RabbitListener(queues = { QueueNames.QUEUE_NAME })
    void receivedMessage(final TextMessage message) {
        LOGGER.info(
                "Received message {} times : {}",
                redisTemplate.opsForValue().get(message),
                message);
    }
}
