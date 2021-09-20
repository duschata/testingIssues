package riderIssue.transactions;

import org.junit.jupiter.api.extension.ExtendWith;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.util.EntityManagerProvider;

@ExtendWith(DBUnitExtension.class)
//just to check inheritance behavior
public class BRiderJunit5TransactionTest extends GenericTest {

    private ConnectionHolder connectionHolder = () -> EntityManagerProvider.instance("databaseRiderTestDB")
            .connection();
}
