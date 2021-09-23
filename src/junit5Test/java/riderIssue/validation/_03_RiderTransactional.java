package riderIssue.validation;

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
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.util.EntityManagerProvider;

import riderIssue.entity.TestEntityWithValidation;
import riderIssue.validation.dao.MyDao;

@EnableAutoWeld
@AddBeanClasses({ //
        EntityManagerFactoryProducer.class, //
        EntityManagerFactory.class, //
        EmProducer.class, //
        ValidatorFactoryImpl.class, //
        MyDao.class, //
        BeanManagerImpl.class })
@AddExtensions({ ValidationExtension.class })
@ExtendWith(DBUnitExtension.class)
@DataSet(transactional = true)
public class _03_RiderTransactional {

    @Inject
    protected EntityManagerProvider entityManagerProvider;

    protected ConnectionHolder connectionHolder = () -> entityManagerProvider.connection();

    @Test //transactional = true startet em2
    public void shouldUseTheValidatorWihtInjectedBean_01() {

        Assertions.assertThat(getEm().getTransaction().isActive()).isTrue(); //em1
        TestEntityWithValidation testEntityWithValidation = new TestEntityWithValidation();
        testEntityWithValidation.setStringField("AString");

        Assertions.assertThatThrownBy(() -> {
            getEm().persist(testEntityWithValidation); //em1
        }).isInstanceOf(ConstraintViolationException.class);
    }

    //weld weg

    //weld erzeugt em3

    @Test //transactional = true startet em3
    public void shouldUseTheValidatorWihtInjectedBean_02() {

        Assertions.assertThat(getEm().getTransaction().isActive()).isTrue();

        TestEntityWithValidation testEntityWithValidation = new TestEntityWithValidation();
        testEntityWithValidation.setStringField("AString");

        Assertions.assertThatThrownBy(() -> {
            getEm().persist(testEntityWithValidation);
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void shouldUseTheValidatorWihtInjectedBean_03() {

        Assertions.assertThat(getEm().getTransaction().isActive()).isTrue();

        TestEntityWithValidation testEntityWithValidation = new TestEntityWithValidation();
        testEntityWithValidation.setStringField("AString");

        Assertions.assertThatThrownBy(() -> {
            getEm().persist(testEntityWithValidation);
        }).isInstanceOf(ConstraintViolationException.class);
    }

    public EntityManager getEm () {
        return entityManagerProvider.getEm();
    }
}
