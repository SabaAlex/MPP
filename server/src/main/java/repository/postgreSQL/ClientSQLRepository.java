package repository.postgreSQL;

import model.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import repository.Sort;
import repository.SortingRepository;
import repository.postgreSQL.statements.ClientSQLStatements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientSQLRepository implements SortingRepository<Long, Client> {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Client> findOne(Long ID) {
        if(ID == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "select * from client where id=?";
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(sql, new Object[]{ID}, (rs, rowNum) -> processData(rs)));
        }catch(org.springframework.dao.EmptyResultDataAccessException e )
        {
            return Optional.empty();
        }


    }

    @Override
    public List<Client> findAll() {

        String sql = "select * from client";
        return jdbcOperations.query(sql, (rs, rowNum) -> processData(rs));

    }

    public Iterable<Client> findAll(Sort sort)
    {
        String sql = "select * from client";
        return sort.sort(jdbcOperations.query(sql, (rs, rowNum) -> processData(rs)));
    }


    protected Client processData(ResultSet resultSet) throws SQLException {
        long ID = resultSet.getLong("id");
        String firstName = resultSet.getString("firstname");
        String lastName = resultSet.getString("lastname");
        int age = resultSet.getInt("age");

        return new Client(ID, firstName, lastName, age);
    }

    @Override
    public Optional<Client> save(Client entity) {
        String sql = ClientSQLStatements.INSERT.composeMessage("client");
        Optional<Client> opt = findOne(entity.getId());
        if (opt.isPresent())
            return opt;
        jdbcOperations.update(sql, entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getAge());
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long aLong) {
        String sql = ClientSQLStatements.DELETE.composeMessage("client");
        Optional<Client> opt=findOne(aLong);
        if (!opt.isPresent())
            return Optional.empty();
        jdbcOperations.update(sql, aLong);
        return opt;
    }

    @Override
    public Optional<Client> update(Client entity) {
        String sql= "UPDATE client SET firstname = ? , lastname = ? , age = ? WHERE id = ?";
        Optional<Client> opt=findOne(entity.getId());
        if (!opt.isPresent())
            return Optional.empty();
        jdbcOperations.update(sql,entity.getFirstName(),entity.getLastName(),entity.getAge(),entity.getId());
        return opt;
    }
}
