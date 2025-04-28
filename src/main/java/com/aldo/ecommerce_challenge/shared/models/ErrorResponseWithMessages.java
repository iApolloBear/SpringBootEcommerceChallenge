package com.aldo.ecommerce_challenge.shared.models;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseWithMessages(
    LocalDateTime timestamp, int status, String error, List<String> messages, String path) {}
