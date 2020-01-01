package com.upgrad.quora.service.component;


import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class AuthorizationHelperComponent {


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

}
