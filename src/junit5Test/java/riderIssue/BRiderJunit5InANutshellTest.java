package riderIssue;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.util.EntityManagerProvider;
import de.riderIssues.TestEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ExtendWith(DBUnitExtension.class)
public class BRiderJunit5InANutshellTest {

    private ConnectionHolder connectionHolder = () -> EntityManagerProvider.instance("databaseRiderTestDB")
            .connection();

    private void startTransaction() {
        getEntityManager().getTransaction().begin();
    }

    private void commitTransaction() {
        try {
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
        }
    }

    @Test
    @DataSet(value = "testentity.xml", cleanBefore = true, transactional = true)
    @ExpectedDataSet(value = "testentity_after.xml")
    public void shouldPassTransactionIsExplictStartedAndCommited() {

        Assertions.assertThat(getEntityManager().getTransaction().isActive()).isTrue();

        Query query = getEntityManager().createQuery("select e from testentity e where e.id = 1l");
        TestEntity singleResult = (TestEntity) query.getSingleResult();

        Assertions.assertThat(singleResult.getStringField()).isEqualTo("Mueller");

        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("Merkel");
        getEntityManager().persist(testEntity);

        List<TestEntity> testEntities = loadAll("id");

        Assertions.assertThat(testEntities.size()).isEqualTo(5);
    }

    private List<TestEntity> loadAll(final String orderBy) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TestEntity> cQuery = getEntityManager().getCriteriaBuilder().createQuery(TestEntity.class);
        Root<TestEntity> c = cQuery.from(TestEntity.class);
        cQuery.select(c);
        cQuery.orderBy(cb.asc(c.get(orderBy)));
        TypedQuery<TestEntity> query = getEntityManager().createQuery(cQuery);

        return query.getResultList();
    }

    private EntityManager getEntityManager() {
        return EntityManagerProvider.instance("databaseRiderTestDB").getEm();
    }

}
