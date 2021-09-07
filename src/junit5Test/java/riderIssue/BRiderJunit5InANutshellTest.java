package riderIssue;

import org.junit.jupiter.api.extension.ExtendWith;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.util.EntityManagerProvider;

@ExtendWith(DBUnitExtension.class)
public class BRiderJunit5InANutshellTest extends GenericTest {

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
}
