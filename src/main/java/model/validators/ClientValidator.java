package model.validators;

import model.domain.Client;
import model.exceptions.ValidatorException;

import java.time.Year;
import java.util.Objects;

public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client entity) throws ValidatorException {
        if (Objects.equals(entity.getAge(), 0))
            throw new ValidatorException("Age is wrong");

        if (Objects.equals(entity.getfName(), ""))
            throw new ValidatorException("First Name is empty");

        if (Objects.equals(entity.getlName(), ""))
            throw new ValidatorException("Last Name is empty");

        if (Objects.equals(entity.getClientNumber(), ""))
            throw new ValidatorException("Client Number  is empty");

        if (Objects.equals(entity.getId(), null))
            throw new ValidatorException("Id is empty");
    }
}
