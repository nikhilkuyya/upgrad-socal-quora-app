package com.upgrad.quora.service.business;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.upgrad.quora.service.common.HibernateHelperService;
import com.upgrad.quora.service.constants.ErrorCodeConstants;
import com.upgrad.quora.service.constants.ErrorMessage;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import com.upgrad.quora.service.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorizationService authorizationService;

    @Transactional
    public UserEntity deleteUser(final String bearerToken, final String uuId)
            throws AuthorizationFailedException, UserNotFoundException {
        UserEntity userEntity = authorizationService.validateTokenandFetchUserByUUID(bearerToken, uuId);

        if (userEntity == null) {
            throw new UserNotFoundException(ErrorCodeConstants.UserNotFoundWithUUID.getCode(),
                    ErrorMessage.DeleteUserNotFoundWithUUID.getErrorMessage());
        }
        DecodedJWT decodedJWT = JWT.decode(bearerToken);
        List<String> jwtUserAudience = decodedJWT.getAudience();
        String jwtUser = jwtUserAudience.get(0);
        UserEntity loginUserProxy = userDao.getUserByUUID(jwtUser);
        UserEntity loggedUserEntity = HibernateHelperService.initializeAndUnproxy(loginUserProxy);
        if (!loggedUserEntity.getRole().equals(Role.Admin.getValue())) {
            throw new AuthorizationFailedException(ErrorCodeConstants.USERDELTEACTIONUNAUTHORIZED.getCode(),
                    ErrorMessage.USERDELTEACTIONUNAUTHORIZED.getErrorMessage());
        }

        return userDao.deleteUser(userEntity);
    }


}
