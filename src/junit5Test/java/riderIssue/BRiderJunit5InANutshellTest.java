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
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.util.EntityManagerProvider;
import com.github.database.rider.junit5.DBUnitExtension;

import de.riderIssues.TestEntity;

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
    public void shouldFailNoTransaction() {
        EntityManager em = getEntityManager();

        Assertions.assertThat(em.getTransaction().isActive()).isTrue();

        Query query = em.createQuery("select e from testentity e where e.id = 1l");
        TestEntity singleResult = (TestEntity) query.getSingleResult();

        Assertions.assertThat(singleResult.getStringField()).isEqualTo("Mueller");

        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("Merkel");
        em.persist(testEntity);

        List<TestEntity> testEntities = loadAll("id");

        //        Never reached
        Assertions.assertThat(testEntities.size()).isEqualTo(5);
    }

    @Test
    @DataSet(value = "testentity.xml", cleanBefore = true, transactional = true)
    @ExpectedDataSet(value = "empty.yml")
    public void shouldPassTransactionIsExplictStartedAndCommited() {

        EntityManager em = getEntityManager();

        boolean active = em.getTransaction().isActive();

        Assertions.assertThat(em.getTransaction().isActive()).isFalse();
        startTransaction();
        Assertions.assertThat(em.getTransaction().isActive()).isTrue();

        Query query = em.createQuery("select e from testentity e where e.id = 1l");
        TestEntity singleResult = (TestEntity) query.getSingleResult();

        Assertions.assertThat(singleResult.getStringField()).isEqualTo("Mueller");

        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("Merkel");
        em.persist(testEntity);

        List<TestEntity> testEntities = loadAll("id");

        Assertions.assertThat(testEntities.size()).isEqualTo(5);

        commitTransaction();
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
