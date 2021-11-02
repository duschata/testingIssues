/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package riderIssue.transactional.support;

import org.jnp.server.NamingBeanImpl;
import org.junit.rules.ExternalResource;

import com.arjuna.ats.jta.utils.JNDIManager;

import javax.naming.Context;

public class JtaEnvironment  {

    private NamingBeanImpl NAMING_BEAN;

    public void before() throws Throwable {

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");

        NAMING_BEAN = new NamingBeanImpl();
        NAMING_BEAN.start();

        JNDIManager.bindJTAImplementation();

        //hier wird die ds programmatisch konfiguriert
        TransactionalConnectionProvider.bindDataSource();
    }

    public void after() {
        NAMING_BEAN.stop();
    }
}
