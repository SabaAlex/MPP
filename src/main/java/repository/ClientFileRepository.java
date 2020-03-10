package repository;

import model.domain.Client;
import model.exceptions.ValidatorException;
import model.validators.Validator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ClientFileRepository extends InMemoryRepository<Long, Client> {
    private String fileName;

    public ClientFileRepository(Validator<Client> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String firstName=items.get(1);
                String lastName=items.get(2);
                int age=Integer.valueOf(items.get(3));


                Client client= new Client(id,firstName,lastName,age);

                try {
                    super.save(client);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    public void saveToFile(){
        Path path = Paths.get(fileName);

        Iterable<Client> entityList = super.findAll();

        try (PrintWriter printWriter = new PrintWriter(this.fileName)) {
            printWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityList.forEach(entity -> {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                bufferedWriter.write(
                        entity.getId() + "," + entity.getFirstName() + "," + entity.getLastName()+","+ entity.getAge());
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + ","  + entity.getFirstName() + "," + entity.getLastName()+","+
                            entity.getAge());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
