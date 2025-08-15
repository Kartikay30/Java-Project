package com.projetanagement.projects.management.system.Response;




public class MessageResponse {
    private String message;

    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    // Add getters/setters if needed
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}