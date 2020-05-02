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
public class Movie extends BaseEntity<Long> implements Serializable {
    private String title;
    private int yearOfRelease;
    private String mainStar;
    private String director;
    private  String genre;
}
