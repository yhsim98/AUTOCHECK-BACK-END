package com.auto.check.util;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Component
public class EntityManagerFactoryUtil {
    EntityManagerFactory enf = Persistence.createEntityManagerFactory("hello");

    public EntityManager getEntityManager(){
        return enf.createEntityManager();
    }
}
