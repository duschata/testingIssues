/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jpacditesting;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import org.hibernate.envers.Audited;

@Entity(name = "TransactionalTestEntity")
@Table(name = "TransactionalTable")
//@EntityListeners(TestListener.class)
@Audited
public class TestEntity {

    @Id
    public UUID id;

    public String name;
}
