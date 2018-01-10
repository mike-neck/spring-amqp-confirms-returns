package com.example;

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

    private final MessageService messageService;

    public MessageController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    ResponseEntity<Messages> getMessages() {
        return ResponseEntity.ok(messageService.getCurrentMessages());
    }

    @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    ResponseEntity<Messages> newMessage(@RequestParam("text") final String text) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(messageService.createNewMessage(new TextMessage(text)));
    }
}
