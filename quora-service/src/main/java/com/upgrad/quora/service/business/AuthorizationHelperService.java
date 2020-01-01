package com.upgrad.quora.service.business;

import com.upgrad.quora.service.constants.ErrorCodeConstants;
import com.upgrad.quora.service.constants.ErrorMessage;
import com.upgrad.quora.service.constants.TokenPrefixes;
import com.upgrad.quora.service.dao.UserAuthTokenDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Base64;

@Service
public class AuthorizationHelperService {

    @Autowired
    private UserAuthTokenDao userAuthTokenDao;

    @Autowired
    private UserDao userDao;

    public UserAuthTokenEntity getUserAuthTokenEntity(final String accesToken) {
        return userAuthTokenDao.getUserAuthToken(accesToken);
    }

    public boolean isValidUserAuthTokenEntity(UserAuthTokenEntity userAuthTokenEntity) {
        boolean isValid = false;
        final ZonedDateTime curretTime = ZonedDateTime.now();
        if ( userAuthTokenEntity != null &&
                userAuthTokenEntity.getLogoutAt() == null &&
                userAuthTokenEntity.getExpiresAt().isAfter(curretTime)){
            isValid = true;
        }
        return isValid;
    }

    public String getBearerToken(final String authorizationHeader){
        final String encodedJwtToken = authorizationHeader.split(TokenPrefixes.BearerToken.getTokenPrefix())[1];
        return encodedJwtToken;
    }

    public String getBasicToken(final String authorizationHeader){
        final String encodedBasicToken = authorizationHeader.split(TokenPrefixes.BasicToken.getTokenPrefix())[1];
        return encodedBasicToken;
    }

    public String[] getUserDetailsFromBearToken(final String basicToken) {
        final byte[] decodeTokenBytes =  Base64.getDecoder().decode(basicToken);
        final String[] userDetails = new String(decodeTokenBytes).split(":");
        return userDetails;
    }

    public UserEntity validateTokenandFetchUserByUUID(final String accessToken,
                                                      final String userUuid) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthTokenEntity = getUserAuthTokenEntity(accessToken);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException(ErrorCodeConstants.UserHasNotSignedIn.getCode(),
                    ErrorMessage.UserHasNotSignedIn.getErrorMessage());
        }

        if(!isValidUserAuthTokenEntity(userAuthTokenEntity)){
            throw new AuthorizationFailedException(ErrorCodeConstants.UserHasSignedOut.getCode(),
                    ErrorMessage.UserHasSignedOut.getErrorMessage());
        }

        UserEntity userEntity = userDao.getUserByUUID(userUuid);
        return  userEntity;
    }
}
