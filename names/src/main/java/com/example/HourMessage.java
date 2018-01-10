package com.example;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
public class HourMessage implements Serializable {

    private static final long serialVersionUID = -5760158911739920821L;

    private final int hour;
    private final TextMessage textMessage;
}
