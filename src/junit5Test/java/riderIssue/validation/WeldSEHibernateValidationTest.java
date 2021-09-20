package riderIssue.validation;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.cdi.ValidationExtension;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddExtensions;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.jboss.weld.manager.BeanManagerImpl;
import org.junit.jupiter.api.Test;

import riderIssue.validation.dao.MyDao;
import riderIssue.entity.TestEntityWithValidation;

@EnableAutoWeld
@AddBeanClasses({ //
        EntityManagerFactoryProducer.class, //
        EntityManagerFactory.class, //
        DefaultEm.class, //
        ValidatorFactoryImpl.class, //
        MyDao.class, //
        BeanManagerImpl.class })
@AddExtensions({ ValidationExtension.class })
public class WeldSEHibernateValidationTest {

    @Inject
    private BeanManager beanManager;

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
    public void shouldUseTheValidatorWihtInjectedBean_01() {
        startTransaction();

        TestEntityWithValidation testEntityWithValidation = new TestEntityWithValidation();
        testEntityWithValidation.setStringField("AString");

        Assertions.assertThatThrownBy(() -> {
            em.persist(testEntityWithValidation);
        }).isInstanceOf(ConstraintViolationException.class);

        commitTransaction();
    }

    @Test
    public void shouldUseTheValidatorWihtInjectedBean_02() {
        startTransaction();

        TestEntityWithValidation testEntityWithValidation = new TestEntityWithValidation();
        testEntityWithValidation.setStringField("AString");

        Assertions.assertThatThrownBy(() -> {
            em.persist(testEntityWithValidation);
        }).isInstanceOf(ConstraintViolationException.class);

        commitTransaction();
    }

    @Test
    public void shouldUseTheValidatorWihtInjectedBean_03() {
        startTransaction();

        TestEntityWithValidation testEntityWithValidation = new TestEntityWithValidation();
        testEntityWithValidation.setStringField("AString");

        Assertions.assertThatThrownBy(() -> {
            em.persist(testEntityWithValidation);
        }).isInstanceOf(ConstraintViolationException.class);

        commitTransaction();
    }
}
