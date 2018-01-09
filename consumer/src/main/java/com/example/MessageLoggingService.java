package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageLoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApp.class);

    @RabbitListener(queues = { QueueNames.QUEUE_NAME })
    void receivedMessage(final TextMessage message) {
        LOGGER.info("Received message : {}", message);
    }
}
