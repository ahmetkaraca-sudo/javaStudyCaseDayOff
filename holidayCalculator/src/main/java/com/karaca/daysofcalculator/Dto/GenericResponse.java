package com.karaca.daysofcalculator.Dto;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class GenericResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;

    public GenericResponse(HttpStatus status, String message) {
        this.timestamp = Instant.now().toString();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
