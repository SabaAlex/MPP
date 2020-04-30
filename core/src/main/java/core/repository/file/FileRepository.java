package core.repository.file;

import core.model.domain.BaseEntity;
import core.model.exceptions.ValidatorException;
import core.model.validators.Validator;
import core.repository.InMemoryRepository;
import core.repository.SavesToFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class FileRepository<ID extends Serializable, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> implements SavesToFile {

    private String fileName;
    private Validator<T> validator;
    public FileRepository(Validator<T> validator, String fileName){
        this.validator = validator;
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                T entity = this.createEntity(items);

                try {
                    validator.validate(entity);
                    super.save(entity);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Optional<T> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        addToFile(entity);
        return Optional.empty();
    }

    private void addToFile(T entity) {
        Path path = Paths.get(this.fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(formatEntity(entity));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(){
        Path path = Paths.get(fileName);

        Iterable<T> entityList = super.findAll();

        try (PrintWriter printWriter = new PrintWriter(this.fileName)) {
            printWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityList.forEach(entity -> {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                bufferedWriter.write(this.formatEntity(entity));
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public String getFileName() {
        return fileName;
    }

    protected abstract String formatEntity(T entity);

    protected abstract T createEntity(List<String> items);
}
