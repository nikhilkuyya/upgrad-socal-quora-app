package com.upgrad.quora.service.constants;

public enum ErrorMessage {
    UserNameAlreadyExist("Try any other Username, this Username has already been taken"),
    UserEmailIdAlreadyExist("This user has already been registered, try with any other emailId");

    private String errorMessage;
    ErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
