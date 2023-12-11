package com.bookloansystem.backend.common.utils;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class UUIDGenerator {
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
