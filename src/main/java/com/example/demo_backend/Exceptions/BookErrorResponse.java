package com.example.demo_backend.Exceptions;

public record BookErrorResponse(int status, String message, long timeStamp) {
}
