package com.control.panel.conversor.dto;

import org.springframework.http.HttpStatus;

public record StatusResponseDTO(String message, Object data, boolean isError) {
}
