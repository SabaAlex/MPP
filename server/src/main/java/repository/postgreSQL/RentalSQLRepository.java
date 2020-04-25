package repository.postgreSQL;

import model.domain.Rental;
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

public class RentalSQLRepository implements SortingRepository<Long, Rental> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Rental> findOne(Long ID) {
        if(ID == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "select * from rental where id=?";
        try{
        return Optional.ofNullable(jdbcOperations.queryForObject(sql,new Object[]{ID}, (rs, rowNum) ->processData(rs)));
        }
        catch(org.springframework.dao.EmptyResultDataAccessException e )
        {
            return Optional.empty();
        }

    }

    @Override
    public List<Rental> findAll() {

        String sql = "select * from rental";
        return jdbcOperations.query(sql, (rs, rowNum) -> processData(rs));

    }

    public Iterable<Rental> findAll(Sort sort)
    {
        String sql = "select * from rental";
        return sort.sort(jdbcOperations.query(sql, (rs, rowNum) -> processData(rs)));
    }

    @Override
    public Optional<Rental> save(Rental entity) {
        String sql = MovieSQLStatements.INSERT.composeMessage("rental");
        Optional<Rental> opt = findOne(entity.getId());
        if (opt.isPresent())
            return opt;
        jdbcOperations.update(sql, entity.getId(), entity.getClientID(),entity.getMovieID(),entity.getDay(),entity.getMonth(),entity.getYear());
        return findOne(entity.getId());
    }

    protected Rental processData(ResultSet resultSet) throws SQLException {
        long ID = resultSet.getLong(1);
        long clientid = resultSet.getLong(2);
        long movieid = resultSet.getLong(3);
        int day = resultSet.getInt(4);
        int month = resultSet.getInt(5);
        int year = resultSet.getInt(6);

        return new Rental(ID, clientid, movieid , year, month, day);
    }

    @Override
    public Optional<Rental> delete(Long aLong) {
        String sql = ClientSQLStatements.DELETE.composeMessage("rental");
        Optional<Rental> opt=findOne(aLong);
        if (!opt.isPresent())
            return opt;
        jdbcOperations.update(sql, aLong);
        return opt;
    }

    public Optional<Rental> update(Rental entity) {
        String sql= "UPDATE client SET day = ? , month = ? , year = ? WHERE id = ?";
        Optional<Rental> opt=findOne(entity.getId());
        if (!opt.isPresent())
            return opt;
        jdbcOperations.update(sql,entity.getDay(),entity.getMonth(),entity.getYear(),entity.getId());
        return opt;
    }


}
