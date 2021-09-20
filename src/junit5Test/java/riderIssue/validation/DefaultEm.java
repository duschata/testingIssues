package riderIssue.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ApplicationScoped
public class DefaultEm {

    @Inject
    private EntityManagerFactory entityManagerFactory;


    @Produces
    public EntityManager produceEntityManager() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }
}
