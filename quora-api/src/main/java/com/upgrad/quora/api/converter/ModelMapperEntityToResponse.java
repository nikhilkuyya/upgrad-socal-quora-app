package com.upgrad.quora.api.converter;

import com.upgrad.quora.api.model.SignupUserResponse;

public class ModelMapperEntityToResponse {

    public static SignupUserResponse getSignupResponse(final String uuid,final String statusMessage) {
        SignupUserResponse userResponse = null;
        if (uuid != null) {
            userResponse = new SignupUserResponse();
            userResponse.setId(uuid);
            userResponse.setStatus(statusMessage);
        }
        return userResponse;
    }
}
