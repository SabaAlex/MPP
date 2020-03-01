package repository;

import model.domain.Movie;
import model.domain.validators.Validator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MovieFileRepository extends InMemoryRepository<Long, Movie> {
    private String fileName;

    public MovieFileRepository(Validator<Movie> validator, String fileName) {
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
                String movieNumber=items.get(1);
                String title=items.get(2);
                String genre=items.get(3);
                int yearOfRelease= Integer.valueOf(items.get(4));
                String director=items.get(5);
                String mainStar=items.get(6);

                Movie movie = new Movie(movieNumber,title,yearOfRelease,mainStar,director,genre);
                movie.setId(id);

                try {
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

    private void saveToFile(Movie entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getMovieNumber() + "," + entity.getTitle() + "," + entity.getYearOfRelease()+","+
                            entity.getMainStar()+","+entity.getDirector()+","+entity.getGenre());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}