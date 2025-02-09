package repository.file;

import model.domain.Movie;

import model.validators.Validator;

import java.util.List;

public class MovieFileRepository extends FileRepository<Long, Movie> {

    public MovieFileRepository(Validator<Movie> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    protected String formatEntity(Movie entity) {
        return entity.getId() + ","  + entity.getTitle() + "," + entity.getGenre()+","+
                entity.getYearOfRelease()+","+entity.getDirector()+","+entity.getMainStar();
    }

    @Override
    protected Movie createEntity(List<String> items) {
        Long id = Long.valueOf(items.get(0));
        String title=items.get(1);
        String genre=items.get(2);
        int yearOfRelease= Integer.parseInt(items.get(3));
        String director=items.get(4);
        String mainStar=items.get(5);

        return new Movie(id,title,yearOfRelease,mainStar,director,genre);
    }

}
