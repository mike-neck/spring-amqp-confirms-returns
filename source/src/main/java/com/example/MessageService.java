package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private static final List<TextMessage> MESSAGES = new ArrayList<>();

    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate<TextMessage, Integer> redisTemplate;
    private final MessageIdentifier messageIdentifier;

    public MessageService(final RabbitTemplate rabbitTemplate,
            final RedisTemplate<TextMessage, Integer> redisTemplate,
            final MessageIdentifier messageIdentifier) {
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
        this.messageIdentifier = messageIdentifier;
    }

    public Messages createNewMessage(final TextMessage textMessage) {
        MESSAGES.add(textMessage);
        final ValueOperations<TextMessage, Integer> operations = redisTemplate.opsForValue();
        if (!operations.setIfAbsent(textMessage, 1)) {
            final Integer current = operations.get(textMessage);
            operations.set(textMessage, current + 1);
        }
        rabbitTemplate.convertAndSend(
                "",
                QueueNames.QUEUE_NAME,
                textMessage,
                new CorrelationData(messageIdentifier.get()));
        return new Messages(currentMessages());
    }

    public Messages getCurrentMessages() {
        return new Messages(currentMessages());
    }

    private List<TextMessage> currentMessages() {
        return Collections.unmodifiableList(MESSAGES);
    }
}
