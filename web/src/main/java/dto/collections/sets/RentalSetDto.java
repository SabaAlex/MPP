package dto.collections.sets;

import dto.MovieDto;
import dto.RentalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalSetDto {
    private Set<RentalDto> rentalDtoSet;
}
