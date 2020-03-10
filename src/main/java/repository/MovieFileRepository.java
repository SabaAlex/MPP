package repository;

import model.domain.Client;
import model.domain.Movie;

import model.exceptions.ValidatorException;
import model.validators.Validator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MovieFileRepository extends InMemoryRepository<Long, Movie> {
    private String fileName;
    private Validator<Movie> validator;
    public MovieFileRepository(Validator<Movie> validator, String fileName) {
        this.validator=validator;
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String title=items.get(1);
                String genre=items.get(2);
                int yearOfRelease= Integer.valueOf(items.get(3));
                String director=items.get(4);
                String mainStar=items.get(5);

                Movie movie = new Movie(id,title,yearOfRelease,mainStar,director,genre);

                try {
                    validator.validate(movie);
                    super.save(movie);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException {
        Optional<Movie> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    public void saveToFile(){
        Path path = Paths.get(fileName);
        Iterable<Movie> entityList = super.findAll();

        try (PrintWriter printWriter = new PrintWriter(this.fileName)) {
            printWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityList.forEach(entity -> {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                bufferedWriter.write(
                        entity.getId() + ","  + entity.getTitle() + "," + entity.getGenre()+","+
                                entity.getYearOfRelease()+","+entity.getDirector()+","+entity.getMainStar());
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveToFile(Movie entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + ","  + entity.getTitle() + "," + entity.getGenre()+","+
                            entity.getYearOfRelease()+","+entity.getDirector()+","+entity.getMainStar());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
