package com.example;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("WeakerAccess")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMessage implements Serializable {
    private static final long serialVersionUID = -6698580864613985749L;
    private String text;
}
