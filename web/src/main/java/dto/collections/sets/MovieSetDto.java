package dto.collections.sets;

import dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieSetDto {
    private Set<MovieDto> movieDtoSet;
}
