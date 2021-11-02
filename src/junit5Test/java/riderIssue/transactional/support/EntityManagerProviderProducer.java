/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package riderIssue.transactional.support;

import com.github.database.rider.core.util.EntityManagerProvider;
import org.hibernate.cfg.Environment;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class EntityManagerProviderProducer {

    @Inject
    private BeanManager beanManager;


    @Produces
    @ApplicationScoped
    public EntityManagerProvider produceEntityManager() {
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.bean.manager", beanManager);
        props.put(Environment.CONNECTION_PROVIDER, TransactionalConnectionProvider.class);
        return EntityManagerProvider.newInstance("myPu", props);

    }

    public void close(@Disposes EntityManagerProvider entityManagerFactory) {
        entityManagerFactory.getEmf().close();
    }
}
