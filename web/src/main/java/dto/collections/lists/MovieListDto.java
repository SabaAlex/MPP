package dto.collections.lists;

import dto.MovieDto;
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
