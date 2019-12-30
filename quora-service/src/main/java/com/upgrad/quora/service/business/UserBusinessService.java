package com.upgrad.quora.service.business;

import com.upgrad.quora.service.constants.ErrorCodeConstants;
import com.upgrad.quora.service.constants.ErrorMessage;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptoGraphyProvider;

    //TODO: Invalid input -> handle case
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity) throws SignUpRestrictedException {

     if( userEntity != null ) {
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
     return  null;
    }
}
