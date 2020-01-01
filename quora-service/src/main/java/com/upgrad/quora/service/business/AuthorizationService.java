package com.upgrad.quora.service.business;

import com.upgrad.quora.service.component.AuthorizationHelperComponent;
import com.upgrad.quora.service.constants.ErrorCodeConstants;
import com.upgrad.quora.service.constants.ErrorMessage;
import com.upgrad.quora.service.dao.UserAuthTokenDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private UserAuthTokenDao userAuthTokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorizationHelperComponent authorizationHelperComponent;

    public UserAuthTokenEntity getUserAuthTokenEntity(final String accesToken) {
        return userAuthTokenDao.getUserAuthToken(accesToken);
    }


    public UserEntity validateTokenandFetchUserByUUID(final String accessToken,
                                                      final String userUuid) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthTokenEntity = getUserAuthTokenEntity(accessToken);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException(ErrorCodeConstants.UserHasNotSignedIn.getCode(),
                    ErrorMessage.UserHasNotSignedIn.getErrorMessage());
        }

        if(!authorizationHelperComponent.isValidUserAuthTokenEntity(userAuthTokenEntity)) {
            throw new AuthorizationFailedException(ErrorCodeConstants.UserHasSignedOut.getCode(),
                    ErrorMessage.UserHasSignedOut.getErrorMessage());
        }

        UserEntity userEntity = userDao.getUserByUUID(userUuid);
        return  userEntity;
    }

}
