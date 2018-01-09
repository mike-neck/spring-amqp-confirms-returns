package com.example;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("WeakerAccess")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    private List<TextMessage> messages;
}
