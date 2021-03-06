package riderIssue.transactions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.util.EntityManagerProvider;

import riderIssue.QueryUtil;
import riderIssue.entity.TestEntity;

public class GenericTest {

    private EntityManagerProvider entityManagerProvider = EntityManagerProvider.instance("databaseRiderTestDB");

    private ConnectionHolder connectionHolder = () -> entityManagerProvider.connection();

    protected EntityManager getEntityManager() {
        return entityManagerProvider.getEm();
    }

    @Test
    @DataSet(value = "testentity.xml", cleanBefore = true, transactional = true)
    @ExpectedDataSet(value = "testentity_after.xml")
    public void shouldPersistSinceTransactionalIsTrue() {

        Assertions.assertThat(getEntityManager().getTransaction().isActive()).isTrue();

        Query query = getEntityManager().createQuery("select e from testentity e where e.id = 1l");
        TestEntity singleResult = (TestEntity) query.getSingleResult();

        Assertions.assertThat(singleResult.getStringField()).isEqualTo("Mueller");

        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("Merkel");
        getEntityManager().persist(testEntity);

        List<TestEntity> testEntities = QueryUtil.loadAll("id", getEntityManager());

        Assertions.assertThat(testEntities.size()).isEqualTo(5);
    }

    @Test
    @DataSet(cleanAfter = true, transactional = true)
    public void shouldPersistSinceTransactionalIsTrueButNoDataSetGiven() {
        Assertions.assertThat(getEntityManager().getTransaction().isActive()).isTrue();
        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("Merkel");
        getEntityManager().persist(testEntity);
    }

}
