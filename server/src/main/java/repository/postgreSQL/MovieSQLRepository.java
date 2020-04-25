package repository.postgreSQL;

import model.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import repository.Sort;
import repository.SortingRepository;
import repository.postgreSQL.statements.ClientSQLStatements;
import repository.postgreSQL.statements.MovieSQLStatements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MovieSQLRepository implements SortingRepository<Long, Movie> {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Movie> findOne(Long ID) {
        if(ID == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "select * from movie where id=?";
        try {
        return Optional.ofNullable(jdbcOperations.queryForObject(sql,new Object[]{ID}, (rs, rowNum) ->processData(rs)));
        }catch(org.springframework.dao.EmptyResultDataAccessException e )
        {
            return Optional.empty();
        }

}

    @Override
    public List<Movie> findAll() {

        String sql = "select * from movie";
        return jdbcOperations.query(sql, (rs, rowNum) -> processData(rs));

    }

    public Iterable<Movie> findAll(Sort sort)
    {
        String sql = "select * from movie";
        return sort.sort(jdbcOperations.query(sql, (rs, rowNum) -> processData(rs)));
    }


    protected Movie processData(ResultSet resultSet) throws SQLException {
        long ID = resultSet.getLong(1);
        String title = resultSet.getString(2);
        String director = resultSet.getString(3);
        int yearOfRelease = resultSet.getInt(4);
        String mainStar = resultSet.getString(5);
        String genre = resultSet.getString(6);

        return new Movie(ID, title, yearOfRelease ,mainStar, director, genre);
    }

    @Override
    public Optional<Movie> save(Movie entity) {
        String sql = MovieSQLStatements.INSERT.composeMessage("movie");
        Optional<Movie> opt=findOne(entity.getId());
        if (opt.isPresent())
            return opt;
        jdbcOperations.update(sql, entity.getId(), entity.getTitle(),entity.getDirector(),entity.getYearOfRelease(),entity.getMainStar(),entity.getMainStar());
        return findOne(entity.getId());
    }

    @Override
    public Optional<Movie> delete(Long aLong) {
        String sql = ClientSQLStatements.DELETE.composeMessage("movie");
        Optional<Movie> opt=findOne(aLong);
        if (!opt.isPresent())
            return Optional.empty();
        jdbcOperations.update(sql, aLong);
        return opt;
    }

    @Override
    public Optional<Movie> update(Movie entity) {
        String sql= "UPDATE client SET title = ? , director = ? , yearofrelease = ?, mainstar = ?, genre = ? WHERE id = ?";
        Optional<Movie> opt=findOne(entity.getId());
        if (!opt.isPresent())
            return Optional.empty();
        jdbcOperations.update(sql,entity.getTitle(),entity.getDirector(),entity.getYearOfRelease(),entity.getMainStar(),entity.getGenre(),entity.getId());
        return opt;
    }
}
