package model.validators;

import model.domain.Movie;
import model.exceptions.ValidatorException;

import java.time.Year;
import java.util.Objects;

public class MovieValidator implements Validator<Movie> {

    @Override
    public void validate(Movie entity) throws ValidatorException {
        if (Objects.equals(entity.getTitle(), ""))
            throw new ValidatorException("Title is empty");

        if (Objects.equals(entity.getMovieNumber(), ""))
            throw new ValidatorException("Movie Number is empty");

        if (Objects.equals(entity.getDirector(), ""))
            throw new ValidatorException("Director is empty");

        if (Objects.equals(entity.getMainStar(), ""))
            throw new ValidatorException("Main Star is empty");

        if (Objects.equals(entity.getYearOfRelease(), Year.now().getValue()))
            throw new ValidatorException("Year is wrong");

        if (Objects.equals(entity.getId(), null))
            throw new ValidatorException("Id is empty");
    }
}
