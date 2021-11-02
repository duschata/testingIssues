import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.util.EntityManagerProvider;

import riderIssue.QueryUtil;
import riderIssue.entity.TestEntity;

public class SimpleTest {

    @Rule
    public EntityManagerProvider emProvider = EntityManagerProvider.instance("databaseRiderTestDB");

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(emProvider.connection());

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

    private EntityManager getEntityManager() {
        return emProvider.getEm();
    }

}
