package com.upgrad.quora.service.constants;

public enum ErrorCodeConstants {

    UserNameAlreadyExist("SGR-001"),
    UserEmailAlreadyExist("SGR-002"),
    UserNameNotValidInput("ATH-001"),
    PasswordInvalidInput("ATH-002");

    private String code;
    ErrorCodeConstants(final String code){
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
