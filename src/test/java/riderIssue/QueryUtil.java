package riderIssue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import riderIssue.entity.TestEntity;

public class QueryUtil {

    public static List<TestEntity> loadAll(final String orderBy, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TestEntity> cQuery = em.getCriteriaBuilder().createQuery(TestEntity.class);
        Root<TestEntity> c = cQuery.from(TestEntity.class);
        cQuery.select(c);
        cQuery.orderBy(cb.asc(c.get(orderBy)));
        TypedQuery<TestEntity> query = em.createQuery(cQuery);

        return query.getResultList();
    }
}
