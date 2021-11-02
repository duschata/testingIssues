/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package riderIssue.transactional.support;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.util.EntityManagerProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.sql.Connection;

@ApplicationScoped
public class EntityManagerProducer {


    @Inject
    private EntityManagerProvider entityManagerProvider;

    @Produces
    public ConnectionHolder createConnection (){
        return () -> entityManagerProvider.connection();
    }

    @Produces
    @RequestScoped
    public EntityManager produceEntityManager() {
        return entityManagerProvider.getEm();
    }

}
