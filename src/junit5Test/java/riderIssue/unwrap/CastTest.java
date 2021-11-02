package riderIssue.unwrap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.validator.cdi.ValidationExtension;
import org.jboss.weld.junit5.auto.ActivateScopes;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddExtensions;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import riderIssue.entity.TestEntity;
import riderIssue.validation.CastCDI;
import riderIssue.validation.EmProducer;
import riderIssue.validation.EntityManagerFactoryProducer;

@EnableAutoWeld
@AddBeanClasses({ //
        EmProducer.class, //
        CastProducer.class, //
        EntityManagerFactoryProducer.class //
})
@ActivateScopes(RequestScoped.class)
@AddExtensions({ ValidationExtension.class })
public class CastTest {

    @Inject
    @CastCDI
    private EntityManager entityManager;

    @Test
    public void doFoo() {

        Assertions.assertThat(entityManager).isNotNull();
        Session session = entityManager.unwrap(Session.class);
        session.createCriteria(TestEntity.class, "test");

    }

}
