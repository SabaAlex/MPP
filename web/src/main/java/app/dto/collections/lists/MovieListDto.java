package app.dto.collections.lists;

import app.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieListDto {
    private List<MovieDto> movieDtoList;
}
