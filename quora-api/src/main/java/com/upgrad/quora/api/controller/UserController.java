package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.converter.ModelMapperEntityToResponse;
import com.upgrad.quora.api.converter.ModelMapperRequestToEntity;
import com.upgrad.quora.api.model.SigninResponse;
import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.UserBusinessService;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import com.upgrad.quora.service.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserBusinessService userService;


    @RequestMapping(method = RequestMethod.POST, path = "/user/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(@RequestBody SignupUserRequest user) throws SignUpRestrictedException {
        UserEntity userEntity = ModelMapperRequestToEntity.mapSigninUserRequestToUserEntity(user);
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setRole(Role.NonAdmin.getValue());
        UserEntity createdUserEntity = userService.signup(userEntity);
        SignupUserResponse signupUserResponse = ModelMapperEntityToResponse.getSignupResponse(
                createdUserEntity.getUuid(),
                "USER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupUserResponse>(signupUserResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/user/signin",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SigninResponse> signin(@RequestHeader("authorization") final String basicToken) throws AuthenticationFailedException {
        final String encodedToken = basicToken.split("Basic ")[1];
        final byte[] decodeTokenBytes =  Base64.getDecoder().decode(encodedToken);
        final String[] userDetails = new String(decodeTokenBytes).split(":");
        UserAuthTokenEntity userAuthTokenEntity = this.userService.signin(userDetails[0],userDetails[1]);
        SigninResponse signinResponse = new SigninResponse().id(userAuthTokenEntity.getUuid()).message("SIGNED IN SUCCESSFULLY'");
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token",userAuthTokenEntity.getAccessToken());
        return new ResponseEntity<SigninResponse>(signinResponse, headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/user/signout",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> signout(@RequestHeader("authorization") final  String bearerToken){
        return null;
    }
}
