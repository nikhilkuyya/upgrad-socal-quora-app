package com.upgrad.quora.service.business;

import com.upgrad.quora.service.constants.ErrorCodeConstants;
import com.upgrad.quora.service.constants.ErrorMessage;
import com.upgrad.quora.service.dao.UserAuthTokenDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;


@Service
public class UserBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthTokenDao userAuthTokenDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptoGraphyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity) throws SignUpRestrictedException {

        UserEntity usersBySameUserName = userDao.getUserByUserName(userEntity.getUsername());
        UserEntity userBySameEmail = userDao.getUserByEmail(userEntity.getEmail());

        if (usersBySameUserName != null) {
            throw new SignUpRestrictedException(ErrorCodeConstants.UserNameAlreadyExist.getCode(),
                    ErrorMessage.UserNameAlreadyExist.getErrorMessage());
        }else if (userBySameEmail != null) {
            throw new SignUpRestrictedException(ErrorCodeConstants.UserEmailAlreadyExist.getCode(),ErrorMessage.UserEmailIdAlreadyExist.getErrorMessage());
        }

         String[] encrytionData =  passwordCryptoGraphyProvider.encrypt(userEntity.getPassword());
         userEntity.setSalt(encrytionData[0]);
         userEntity.setPassword(encrytionData[1]);
         return userDao.createUser(userEntity);
    }

    @Transactional
    public UserAuthTokenEntity signin(final String username,
                                      final String password) throws AuthenticationFailedException {
        UserEntity userEntity =  userDao.getUserByUserName(username);
        if(userEntity == null) {
            throw  new AuthenticationFailedException(ErrorCodeConstants.UserNameNotValidInput.getCode(),
                    ErrorMessage.UserNameDoesnotExist.getErrorMessage());
        }

        final String salt = userEntity.getSalt();
        final String encryptedInputPassword = PasswordCryptographyProvider.encrypt(password,salt);
        final String actualPassword = userEntity.getPassword();
        if(!actualPassword.equals(encryptedInputPassword)){
            throw new AuthenticationFailedException(ErrorCodeConstants.PasswordInvalidInput.getCode(),
                    ErrorMessage.PasswordInvalidInput.getErrorMessage());
        }

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedInputPassword);
        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime expiresAt = currentTime.plusHours(8);
        UserAuthTokenEntity userAuthTokenEntity = new UserAuthTokenEntity();
        userAuthTokenEntity.setUuid(UUID.randomUUID().toString());
        userAuthTokenEntity.setUser(userEntity);
        userAuthTokenEntity.setLoginAt(currentTime);
        userAuthTokenEntity.setExpiresAt(expiresAt);
        userAuthTokenEntity.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(),currentTime,expiresAt));
        return userAuthTokenDao.creatAuthToken(userAuthTokenEntity);
    }

}
