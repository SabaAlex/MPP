package model.validators;

import model.domain.Client;
import model.exceptions.ValidatorException;

import java.util.Optional;

public class ClientValidator implements Validator<Client> {

    /**
     * Validates a Client's attributes to be correct
     *
     * @param entity is created before being called by validate
     * @throws ValidatorException some attribute of the entity does not meet a certain validation criteria
     */
    @Override
    public void validate(Client entity) throws ValidatorException {
        Optional.ofNullable(entity.getFirstName())
                .filter(e -> !e.equals(""))
                .orElseThrow(()-> new ValidatorException("First Name is empty"));

        Optional.ofNullable(entity.getLastName())
                .filter(e -> !e.equals(""))
                .orElseThrow(()-> new ValidatorException("Last Name is empty"));

        Optional.ofNullable(entity.getClientNumber())
                .filter(e -> !e.equals(""))
                .orElseThrow(()-> new ValidatorException("Client Number is empty"));

        Optional.ofNullable(entity.getId())
                .orElseThrow(()-> new ValidatorException("Id is empty"));

        Optional.of(entity.getAge())
                .filter(e -> e > 0)
                .orElseThrow(() -> new ValidatorException("Age is not correct"));

    }
}
