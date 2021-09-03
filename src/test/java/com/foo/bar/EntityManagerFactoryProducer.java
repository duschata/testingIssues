package com.foo.bar;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.cdi.HibernateValidator;

@ApplicationScoped
public class EntityManagerFactoryProducer {

    @Inject
    private ValidatorFactory validatorFactory;

    @Inject
    private Validator validator;

    @Inject
    private BeanManager beanManager;

    @Produces
    public EntityManagerFactory produceEntityManagerFactory() {
        Map<String, Object> props  = new HashMap();
        props.put("javax.persistence.bean.manager", beanManager);
        return Persistence.createEntityManagerFactory("databaseRiderTestDB", props);
    }

    public void close(@Disposes EntityManagerFactory entityManagerFactory) {
        entityManagerFactory.close();
    }
}