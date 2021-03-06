/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jpacditesting;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PostPersist;

@ApplicationScoped
public class TestListener {

    @Inject
    private TestService testService;

    @PostPersist public void onPostPersist(TestEntity entity) {
        testService.addTestEntityName(entity.name);
    }
}
