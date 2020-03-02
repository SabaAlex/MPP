package model.validators;

import model.domain.Client;
import model.exceptions.ValidatorException;

import java.util.Optional;

public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client entity) throws ValidatorException {
        Optional.ofNullable(entity.getfName())
                .filter(e -> !e.equals(""))
                .orElseThrow(()-> new ValidatorException("First Name is empty"));

        Optional.ofNullable(entity.getlName())
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
