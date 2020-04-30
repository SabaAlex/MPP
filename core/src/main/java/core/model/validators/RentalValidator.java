package core.model.validators;

import core.model.domain.Rental;
import core.model.exceptions.ValidatorException;

import java.time.Year;
import java.util.Optional;

public class RentalValidator implements Validator<Rental> {

    /**
     * Validates a Rental's attributes to be correct
     *
     * @param entity is created before being called by validate
     * @throws ValidatorException some attribute of the entity does not meet a certain validation criteria
     */
    @Override
    public void validate(Rental entity) throws ValidatorException {
        Optional.ofNullable(entity.getId())
                .orElseThrow(()-> new ValidatorException("Id is empty"));
        Optional.ofNullable(entity.getClientID())
                .orElseThrow(()->new ValidatorException("Client ID is empty"));
        Optional.ofNullable(entity.getMovieID())
                .orElseThrow(()->new ValidatorException("Movie ID is empty"));
        Optional.ofNullable(entity.getYear())
                .filter(e -> e > 1970 && e <= Year.now().getValue())
                .orElseThrow(()->new ValidatorException("Year is empty or the  value is not between 1970 and this year"));
        Optional.ofNullable(entity.getMonth())
                .filter(e -> e > 0 && e <=12)
                .orElseThrow(()->new ValidatorException("Month is empty or the value is not between 1 and 12"));
        Optional.ofNullable(entity.getDay())
                .filter(e-> e >= 1 && e <= 31 )
                .orElseThrow(()->new ValidatorException("Day is empty or the value is not between 1 and 31"));
    }
}
