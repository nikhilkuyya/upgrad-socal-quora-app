package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.constants.ResponseMessages;
import com.upgrad.quora.api.converter.ModelMapperEntityToResponse;
import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AdminService;
import com.upgrad.quora.service.business.AuthorizationHelperService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthorizationHelperService authorizationHelperService;

    @RequestMapping(method = RequestMethod.DELETE,path = "/admin/user/{userId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDeleteResponse> userDelete(@PathVariable("userId") final String userUuid,
                                                         @RequestHeader("authorization") final String bearerToken)
            throws AuthorizationFailedException, UserNotFoundException {
        String accessToken = authorizationHelperService.getBearerToken(bearerToken);
        UserEntity userEntity = adminService.deleteUser(accessToken,userUuid);
        UserDeleteResponse userDeleteResponse = ModelMapperEntityToResponse.getUserDeleteResponse(
                userEntity.getUuid(),
                ResponseMessages.USERDELETESUCCESS.getResponseMessage());
        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse,
                HttpStatus.OK);
    }
}
