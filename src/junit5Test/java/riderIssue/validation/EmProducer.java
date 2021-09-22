package riderIssue.validation;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ValidatorFactory;

import com.github.database.rider.junit5.util.EntityManagerProvider;

@ApplicationScoped
public class EmProducer {

    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @DefaultCDI
    public EntityManager produceEntityManager() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    @Inject
    private ValidatorFactory validatorFactory;

    @Produces
    @RiderCDI
    public EntityManager createEntityManager() {
        Map<String, Object> props = new HashMap();
        props.put("javax.persistence.validation.factory", validatorFactory);

        return EntityManagerProvider.instance("databaseRiderTestDB", props).getEm();
    }
}
