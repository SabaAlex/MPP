package core.model.domain;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Rental extends BaseEntity<Long> implements Serializable {
    private Long ClientID;
    private Long MovieID;
    private int year;
    private int month;
    private int day;
}
