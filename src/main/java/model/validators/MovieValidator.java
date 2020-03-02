package model.validators;

import model.domain.Movie;
import model.exceptions.ValidatorException;

import java.time.Year;
import java.util.Optional;

public class MovieValidator implements Validator<Movie> {

    @Override
    public void validate(Movie entity) throws ValidatorException {
        Optional.ofNullable(entity.getTitle())
                .filter(e -> !e.equals(""))
                .orElseThrow(()-> new ValidatorException("Title is empty"));

        Optional.ofNullable(entity.getMovieNumber())
                .filter(e -> !e.equals(""))
                .orElseThrow(()-> new ValidatorException("Movie Number is empty"));

        Optional.ofNullable(entity.getDirector())
                .filter(e -> !e.equals(""))
                .orElseThrow(()-> new ValidatorException("Director is empty"));

        Optional.ofNullable(entity.getMainStar())
                .orElseThrow(()-> new ValidatorException("Main Star is empty"));

        Optional.of(entity.getYearOfRelease())
                .filter(e -> e > 0 && e < Year.now().getValue())
                .orElseThrow(()-> new ValidatorException("Year is wrong"));

        Optional.ofNullable(entity.getId())
                .orElseThrow(()-> new ValidatorException("Id is empty"));

    }
}
