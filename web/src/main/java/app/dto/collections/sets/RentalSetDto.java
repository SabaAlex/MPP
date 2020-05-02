package app.dto.collections.sets;

import app.dto.RentalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class RentalSetDto extends SetDto<RentalDto>{
}
