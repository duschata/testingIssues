package com.foo.bar;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.cdi.HibernateValidator;
import org.hibernate.validator.cdi.ValidationExtension;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddExtensions;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.jboss.weld.manager.BeanManagerImpl;
import org.junit.jupiter.api.Test;

import com.foo.bar.dao.MyDao;
import com.foo.bar.entity.TestEntity;

@EnableAutoWeld
@AddBeanClasses({ //
        EntityManagerFactoryProducer.class, EntityManagerFactory.class, //
        DefaultEm.class, //
        ValidatorFactoryImpl.class, //
        MyDao.class, //
        BeanManagerImpl.class })
@AddExtensions({ ValidationExtension.class })
public class WeldSEHibernateValidationTest {

    @Inject
    private BeanManager beanManager;

    @Inject
    @HibernateValidator
    private ValidatorFactory validatorFactory;

    @Inject
    @HibernateValidator
    private Validator validator;

    @Inject
    protected EntityManager em;

    private void startTransaction() {
        em.getTransaction().begin();
    }

    private void commitTransaction() {
        try {
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Test
    public void shouldUseTheValidatorWihtInjectedBean() {
        startTransaction();

        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("AString");
        em.persist(testEntity);

        commitTransaction();
    }
}
