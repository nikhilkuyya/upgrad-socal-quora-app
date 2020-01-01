package com.upgrad.quora.api.constants;

public enum ResponseMessages {
    USERREGISTEREDSUCCESS ("USER SUCCESSFULLY REGISTERED"),
    USERSIGNINSUCCESS("SIGNED IN SUCCESSFULLY"),
    USERSIGNOUTSUCCESS("SIGNED OUT SUCCESSFULLY");

    final String responseMessage;
    ResponseMessages(final String message) {
        this.responseMessage = message;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }
}
