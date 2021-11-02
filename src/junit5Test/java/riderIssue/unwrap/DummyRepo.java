package riderIssue.unwrap;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class DummyRepo {

    @Inject
    private Instance<EntityManager> entityManager;
}
