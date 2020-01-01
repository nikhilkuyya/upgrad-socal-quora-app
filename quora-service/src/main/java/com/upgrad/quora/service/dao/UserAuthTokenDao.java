package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserAuthTokenDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserAuthTokenEntity creatAuthToken(
            UserAuthTokenEntity userAuthTokenEntity) {
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public UserAuthTokenEntity getUserAuthToken(String jwtToken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken",UserAuthTokenEntity.class)
                    .setParameter("accessToken",jwtToken).getSingleResult();
        }catch (NoResultException nre) {
            return  null;
        }

    }

    public UserAuthTokenEntity update(UserAuthTokenEntity userAuthTokenEntity) {
        entityManager.merge(userAuthTokenEntity);
        return  userAuthTokenEntity;
    }



}
