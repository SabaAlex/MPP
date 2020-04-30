package dto.collections.lists;

import dto.RentalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalListDto {
    private List<RentalDto> rentalDtoList;
}
