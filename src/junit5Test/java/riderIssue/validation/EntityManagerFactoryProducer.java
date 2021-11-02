package riderIssue.validation;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ValidatorFactory;

@ApplicationScoped
public class EntityManagerFactoryProducer {

    @Inject
    private ValidatorFactory validatorFactory;

    @Produces
    public EntityManagerFactory produceEntityManagerFactory() {
        Map<String, Object> props = new HashMap();
        props.put("javax.persistence.validation.factory", validatorFactory);
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("databaseRiderTestDB", props);
        return entityManagerFactory;
    }

    public void close(@Disposes EntityManagerFactory entityManagerFactory) {
        entityManagerFactory.close();
    }
}