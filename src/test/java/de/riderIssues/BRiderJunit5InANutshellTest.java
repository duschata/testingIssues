package de.riderIssues;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.util.EntityManagerProvider;
import com.github.database.rider.junit5.DBUnitExtension;

//@EnableWeld
@ExtendWith(DBUnitExtension.class)
public class BRiderJunit5InANutshellTest {

    private ConnectionHolder connectionHolder = () -> EntityManagerProvider.instance("databaseRiderTestDB")
            .connection();

    @Test
    @DataSet(value = "testentity.xml", transactional = false, cleanAfter = false)
    public void doFoo() {
        //        EntityManager em = EntityManagerProvider.instance("databaseRiderTestDB").getEm();
        //
        //        Query query = em.createQuery("select e from testentity e where e.id = 1l");
        //
        //        TestEntity singleResult = (TestEntity) query.getSingleResult();
        //
        //        System.out.println("hello, " + singleResult.getStringField());

        System.out.println("hello");
    }

}
