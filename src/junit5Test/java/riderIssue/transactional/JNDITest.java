package riderIssue.transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.jboss.weld.junit5.auto.ActivateScopes;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddExtensions;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.arjuna.ats.jta.cdi.TransactionExtension;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.util.EntityManagerProvider;
import com.github.database.rider.junit5.incubating.DBUnitExtension;

import jpacditesting.TestEntity;
import riderIssue.transactional.support.EntityManagerProducer;
import riderIssue.transactional.support.EntityManagerProviderProducer;
import riderIssue.transactional.support.JtaEnvironment;

@EnableAutoWeld
@AddBeanClasses({ //
        EntityManagerProducer.class, //
        EntityManagerProviderProducer.class, //
})
@AddExtensions({ TransactionExtension.class })
@ExtendWith(DBUnitExtension.class)
@ActivateScopes(RequestScoped.class)
public class JNDITest {

    private static JtaEnvironment jtaEnvironment;

    @BeforeAll
    public static void initJta() throws Throwable {
        jtaEnvironment = new JtaEnvironment();
        jtaEnvironment.before();
    }

    @AfterAll
    public static void stopJta() {
        jtaEnvironment.after();
    }

    @Inject
    protected EntityManagerProvider entityManagerProvider;

    @Inject
    protected ConnectionHolder connectionHolder;

    @Inject
    EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Test
    @DataSet(cleanAfter = true)
    public void testInjectedRepoNotNull() throws Exception {
        assertThat(entityManager).isNotNull();
    }

    @Test
    @DataSet(cleanAfter = true)
    public void testInjectedRepoNotNull02() throws Exception {
        assertThat(entityManager).isNotNull();
    }

    @Inject
    private UserTransaction ut;

    @Test
    @DataSet(value = "transactionalTestentity.xml")
    public void canInjectUserTransaction01() throws Exception {
        assertThat(ut).isNotNull();

        ut.begin();

        assertThat(getEntityManager().getTransaction().isActive()).isTrue();

        TestEntity te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 1";
        getEntityManager().persist(te);

        te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 2";
        getEntityManager().persist(te);

        ut.commit();
        //        getEntityManager().clear();

        ut.begin();
        List<TestEntity> loaded = getEntityManager().createQuery("FROM TransactionalTestEntity te", TestEntity.class)
                .getResultList();
        assertThat(loaded).hasSize(4);
        ut.commit();
    }

    @Test
    @DataSet(value = "transactionalTestentity.xml")
    public void canInjectUserTransaction02() throws Exception {
        assertThat(ut).isNotNull();

        assertThat(getEntityManager().getTransaction().isActive()).isFalse();

        ut.begin();

        assertThat(getEntityManager().getTransaction().isActive()).isTrue();

        TestEntity te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 1";
        getEntityManager().persist(te);

        te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 2";
        getEntityManager().persist(te);

        ut.commit();
        //        getEntityManager().clear();

        ut.begin();
        List<TestEntity> loaded = getEntityManager().createQuery("FROM TransactionalTestEntity te", TestEntity.class)
                .getResultList();
        assertThat(loaded).hasSize(4);
        ut.commit();
    }
}
