package com.upgrad.quora.service.business;

import com.upgrad.quora.service.constants.ErrorCodeConstants;
import com.upgrad.quora.service.constants.ErrorMessage;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserDao userDao;

    public UserEntity getUserEntity(final String accessToken,
                                    final String userUuid) throws UserNotFoundException, AuthorizationFailedException {

        UserEntity userEntity = authorizationService.validateTokenandFetchUserByUUID(accessToken, userUuid);
        if (userEntity == null) {
            throw new UserNotFoundException(ErrorCodeConstants.UserNotFoundWithUUID.getCode(),
                    ErrorMessage.UserNotFoundWithUUID.getErrorMessage());
        }

        return userEntity;
    }

}
