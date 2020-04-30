package core.model.domain;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @GeneratedValue
    private ID id;

}
