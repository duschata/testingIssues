package riderIssue;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ValidatorFactory;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.cdi.ValidationExtension;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddExtensions;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.util.EntityManagerProvider;
import com.github.database.rider.junit5.DBUnitExtension;

import de.riderIssues.dao.MyDao;
import de.riderIssues.entity.TestEntity;
import de.riderIssues.validation.MyValidator;

@EnableAutoWeld
@ExtendWith(DBUnitExtension.class)
@AddBeanClasses({
        DefaultEm.class, //
        ValidatorFactoryImpl.class, //
        MyValidator.class, //
        MyDao.class
})
@AddExtensions({ ValidationExtension.class })
public class BRiderJunit5InANutshellTest {

    private ConnectionHolder connectionHolder = () -> EntityManagerProvider.instance("databaseRiderTestDB")
            .connection();

    @Inject
    private MyValidator myValidator;

    @Inject
    private ValidatorFactory validatorFactory;

    @Inject
    protected EntityManager em;

    private void startTransaction() {
        em.getTransaction().begin();
    }

    private void commitTransaction() {
        try {
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Test
    @DataSet(value = "testentity.xml", cleanBefore = true, transactional = true)
    public void shouldFailNoTransaction() {

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
    public void shouldPassTransactionIsExplicitStartedAndCommited() {

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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TestEntity> cQuery = em.getCriteriaBuilder().createQuery(TestEntity.class);
        Root<TestEntity> c = cQuery.from(TestEntity.class);
        cQuery.select(c);
        cQuery.orderBy(cb.asc(c.get(orderBy)));
        TypedQuery<TestEntity> query = em.createQuery(cQuery);

        return query.getResultList();
    }
}
