package riderIssue.validation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ValidatorFactory;

import com.github.database.rider.core.util.EntityManagerProvider;

@ApplicationScoped
public class EmProducer {

    @Inject
    private ValidatorFactory validatorFactory;

    @Inject
    private EntityManagerFactory entityManagerFactory;

    protected EntityManagerProvider entityManagerProvider;

    @PostConstruct
    public void init() {
        Map<String, Object> props = new HashMap();
        props.put("javax.persistence.validation.factory", validatorFactory);
        entityManagerProvider = EntityManagerProvider.newInstance("databaseRiderTestDB", props);
    }

    @Produces
    EntityManagerProvider createEntityManagerProvider() {
        return entityManagerProvider;
    }

    @Produces
    public EntityManager produceEntityManagerFactory() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseRiderTestDB");
        return entityManagerFactory.createEntityManager();
    }

    @Produces
    @DefaultCDI
    public EntityManager produceEntityManager() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    @Produces
    @RiderCDI
    public EntityManager createEntityManager() {
        return entityManagerProvider.getEm();
    }

}
