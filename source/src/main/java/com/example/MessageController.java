package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "messages")
public class MessageController {

    private static final List<TextMessage> MESSAGES = new ArrayList<>();

    public MessageController(final RabbitTemplate rabbitTemplate,
            final MessageIdentifier messageIdentifier) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageIdentifier = messageIdentifier;
    }

    private final RabbitTemplate rabbitTemplate;
    private final MessageIdentifier messageIdentifier;

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    ResponseEntity<Messages> getMessages() {
        return ResponseEntity.ok(getTextMessages());
    }

    private Messages getTextMessages() {
        return new Messages(Collections.unmodifiableList(MESSAGES));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    ResponseEntity<Messages> newMessage(@RequestParam("text") final String text) {
        final TextMessage message = new TextMessage(text);

        MESSAGES.add(message);
        rabbitTemplate.convertAndSend("", QueueNames.QUEUE_NAME, message, new CorrelationData(messageIdentifier.get()));

        return ResponseEntity.status(HttpStatus.CREATED).body(getTextMessages());
    }
}
