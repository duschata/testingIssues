package riderIssue;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

import com.github.database.rider.core.util.EntityManagerProvider;

public class DefaultEm {
    @Produces
    public EntityManager getEntityManager() {
        return EntityManagerProvider.instance("databaseRiderTestDB").getEm();
    }
}
