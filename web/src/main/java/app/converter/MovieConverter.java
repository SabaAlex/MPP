package app.converter;

import core.model.domain.Movie;
import app.dto.MovieDto;
import org.springframework.stereotype.Component;

@Component
public class MovieConverter extends BaseConverter<Long, Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .director(dto.getDirector())
                .mainStar(dto.getMainStar())
                .genre(dto.getGenre())
                .yearOfRelease(dto.getYearOfRelease())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .title(movie.getTitle())
                .director(movie.getDirector())
                .mainStar(movie.getMainStar())
                .genre(movie.getGenre())
                .yearOfRelease(movie.getYearOfRelease())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}
