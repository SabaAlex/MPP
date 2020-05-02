package app.dto.collections.lists;

import app.dto.MovieDto;
import app.dto.RentalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RentalListDto extends ListDto<RentalDto> {
}
