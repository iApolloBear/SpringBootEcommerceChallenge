package com.aldo.ecommerce_challenge.shared.models;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timestamp, int status, String error, String message, String path) {}
