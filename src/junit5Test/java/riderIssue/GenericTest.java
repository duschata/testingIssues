package riderIssue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.util.EntityManagerProvider;

import de.riderIssues.TestEntity;

public class GenericTest {

    private List<TestEntity> loadAll(final String orderBy) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TestEntity> cQuery = getEntityManager().getCriteriaBuilder().createQuery(TestEntity.class);
        Root<TestEntity> c = cQuery.from(TestEntity.class);
        cQuery.select(c);
        cQuery.orderBy(cb.asc(c.get(orderBy)));
        TypedQuery<TestEntity> query = getEntityManager().createQuery(cQuery);

        return query.getResultList();
    }

    protected EntityManager getEntityManager() {
        return EntityManagerProvider.instance("databaseRiderTestDB").getEm();
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

        List<TestEntity> testEntities = loadAll("id");

        Assertions.assertThat(testEntities.size()).isEqualTo(5);
    }

    @Test
    @DataSet(cleanAfter = true, transactional = true)
    public void shouldPersistSinceTransactionalIsTrueButNoDataSetGiven () {
        Assertions.assertThat(getEntityManager().getTransaction().isActive()).isTrue();
        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("Merkel");
        getEntityManager().persist(testEntity);
    }

}
