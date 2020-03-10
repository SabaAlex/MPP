package repository;

import model.domain.Client;
import model.domain.Movie;

import model.domain.Rental;
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

public class RentalFileRepository extends InMemoryRepository<Long, Rental> {
    private String fileName;

    public RentalFileRepository(Validator<Rental> validator, String fileName) {
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
                Long ClientId =Long.valueOf(items.get(1));
                Long MovieID= Long.valueOf(items.get(2));
                int year= Integer.valueOf(items.get(3));
                int month =  Integer.valueOf(items.get(4));
                int day= Integer.valueOf(items.get(5));


                Rental rental = new Rental(id,ClientId,MovieID,year,month,day);

                try {
                    super.save(rental);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException {
        Optional<Rental> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Rental entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getClientID() + "," + entity.getMovieID()+","+
                            entity.getYear()+","+entity.getMonth()+","+entity.getDay());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(){
        Path path = Paths.get(fileName);

        Iterable<Rental> entityList = super.findAll();

        try (PrintWriter printWriter = new PrintWriter(this.fileName)) {
            printWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityList.forEach(entity -> {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                bufferedWriter.write(
                        entity.getId() + "," + entity.getClientID() + "," + entity.getMovieID()+","+
                                entity.getYear()+","+entity.getMonth()+","+entity.getDay());
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
