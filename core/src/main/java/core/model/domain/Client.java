package core.model.domain;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Client extends BaseEntity<Long> implements Serializable {
    private String firstName;
    private String lastName;
    private int age;
}
