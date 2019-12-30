package com.upgrad.quora.api.converter;

import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.service.entity.UserEntity;

public class ModelMapperRequestToEntity {

    public static UserEntity mapSigninUserRequestToUserEntity(SignupUserRequest signinUserRequest) {
        UserEntity userEntity = null;
        if(signinUserRequest != null){
            userEntity = new UserEntity();
            userEntity.setFirstname(signinUserRequest.getFirstName());
            userEntity.setLastname(signinUserRequest.getLastName());
            userEntity.setUsername(signinUserRequest.getUserName());
            userEntity.setContactnumber(signinUserRequest.getContactNumber());
            userEntity.setEmail(signinUserRequest.getEmailAddress());
            userEntity.setPassword(signinUserRequest.getPassword());
            userEntity.setCountry(signinUserRequest.getCountry());
            userEntity.setAboutme(signinUserRequest.getAboutMe());
            userEntity.setDob(signinUserRequest.getDob());
        }
        return  userEntity;
    }

}
