package app.dto.collections.sets;

import app.dto.RentalDto;
import app.dto.collections.sets.base.SetDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RentalSetDto extends SetDto<RentalDto> {
}
