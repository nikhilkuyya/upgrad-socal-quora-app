package com.upgrad.quora.api.converter;

import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.entity.UserEntity;

public class ModelMapperEntityToResponse {

    public static SignupUserResponse getSignupResponse(final String uuid, final String statusMessage) {
        SignupUserResponse userResponse = null;
        if (uuid != null) {
            userResponse = new SignupUserResponse();
            userResponse.setId(uuid);
            userResponse.setStatus(statusMessage);
        }
        return userResponse;
    }

    public static UserDetailsResponse getUserDetailsResponse(final UserEntity userEntity) {
        UserDetailsResponse userDetailsResponse = null;
        if (userEntity != null) {
            userDetailsResponse = new UserDetailsResponse()
                    .firstName(userEntity.getFirstname())
                    .lastName(userEntity.getLastname())
                    .userName(userEntity.getUsername())
                    .emailAddress(userEntity.getEmail())
                    .country(userEntity.getCountry())
                    .aboutMe(userEntity.getAboutme())
                    .dob(userEntity.getDob())
                    .contactNumber(userEntity.getContactnumber());
        }
        return userDetailsResponse;
    }

    public static UserDeleteResponse getUserDeleteResponse(final String uuid,
                                                           final String status) {
        UserDeleteResponse userDeleteResponse = null;
        if (uuid != null) {
            userDeleteResponse = new UserDeleteResponse().id(uuid).status(status);
        }
        return userDeleteResponse;

    }

}
