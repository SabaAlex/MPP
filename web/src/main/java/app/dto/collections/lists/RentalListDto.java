package app.dto.collections.lists;

import app.dto.RentalDto;
import app.dto.collections.lists.base.ListDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RentalListDto extends ListDto<RentalDto> {
}
