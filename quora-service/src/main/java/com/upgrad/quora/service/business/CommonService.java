package com.upgrad.quora.service.business;

import com.upgrad.quora.service.constants.ErrorCodeConstants;
import com.upgrad.quora.service.constants.ErrorMessage;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonService {

    @Autowired
    private AuthorizationHelperService authorizationHelperService;

    @Autowired
    private UserDao userDao;

    @Transactional
    public UserEntity getUserEntity(final String accessToken,
                                    final String userUuid) throws UserNotFoundException, AuthorizationFailedException {

        UserAuthTokenEntity userAuthTokenEntity = authorizationHelperService.getUserAuthTokenEntity(accessToken);
        if(userAuthTokenEntity == null) {
            throw new AuthorizationFailedException(ErrorCodeConstants.UserHasNotSignedIn.getCode(),
                    ErrorMessage.UserHasNotSignedIn.getErrorMessage());
        }

        if(!authorizationHelperService.isValidUserAuthTokenEntity(userAuthTokenEntity)){
            throw new AuthorizationFailedException(ErrorCodeConstants.UserHasSignedOut.getCode(),
                    ErrorMessage.UserHasSignedOut.getErrorMessage());
        }

        UserEntity userEntity = userDao.getUserByUUID(userUuid);
        if(userEntity == null) {
            throw new UserNotFoundException(ErrorCodeConstants.UserNotFoundWithUUID.getCode(),
                    ErrorMessage.UserNotFoundWithUUID.getErrorMessage());
        }

        return userEntity;
    }

}
