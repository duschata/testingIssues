package riderIssue.unwrap;

import riderIssue.validation.CastCDI;
import riderIssue.validation.EmProducer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class CastProducer extends EmProducer {


    @Produces
    @CastCDI
//    @RequestScoped
    public EntityManager createEntityManagerLevel() {
        return Persistence.createEntityManagerFactory("databaseRiderTestDB").createEntityManager();
    }
}
