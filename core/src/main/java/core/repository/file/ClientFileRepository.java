package core.repository.file;

import core.model.domain.Client;
import core.model.validators.Validator;

import java.util.List;


public class ClientFileRepository extends FileRepository<Long, Client> {

    public ClientFileRepository(Validator<Client> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    protected String formatEntity(Client entity) {
        return entity.getId() + "," + entity.getFirstName() + "," + entity.getLastName()+","+ entity.getAge();
    }

    @Override
    protected Client createEntity(List<String> items) {
        Long id = Long.valueOf(items.get(0));
        String firstName=items.get(1);
        String lastName=items.get(2);
        int age = Integer.parseInt(items.get(3));

        return new Client(firstName,lastName,age);
    }
}
