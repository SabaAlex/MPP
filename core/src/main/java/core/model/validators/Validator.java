package core.model.validators;

import core.model.exceptions.ValidatorException;

public interface Validator<T> {
    /**
     * Validates the entity object
     *
     * @param entity is created before being called by validate
     * @throws ValidatorException some attribute of the entity does not meet a certain validation criteria
     */
    void validate(T entity) throws ValidatorException;
}
