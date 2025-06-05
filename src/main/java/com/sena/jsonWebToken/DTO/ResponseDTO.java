package com.sena.jsonWebToken.DTO;

public class ResponseDTO {
    private String status;
    private String message;
    private String token;
    private Object data;

    // Constructores
    public ResponseDTO() {}

    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDTO(String status, String message, String token, Object data) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.data = data;
    }

    // Getters y Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}