package com.control.conversor.dto;

public record ResponseDTO(String message,Integer status,Object data,String timestamp,String path) {
}