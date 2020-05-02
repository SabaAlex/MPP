package app.dto.collections.sets;

import app.dto.MovieDto;
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
