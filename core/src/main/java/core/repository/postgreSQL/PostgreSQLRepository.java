package core.repository.postgreSQL;

import core.model.domain.BaseEntity;
import core.model.exceptions.ValidatorException;
import core.repository.Sort;
import core.repository.SortingRepository;

import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class PostgreSQLRepository<ID extends Serializable, T extends BaseEntity<ID>> implements SortingRepository<ID, T> {

    private static final String URL = "jdbc:postgresql://localhost:5432/MPP";
    private static final String UserName = System.getProperty("username");
    private static final String Password = System.getProperty("password");
    private String tableName;
    protected Connection conn;
    private Map<ID, T> entities;

    public String getTableName() {
        return tableName;
    }

    public PostgreSQLRepository(String tableName) throws SQLException {
        conn = DriverManager.getConnection(URL,UserName,Password);
        this.tableName = tableName;
        entities = new HashMap<>();

        loadData();
    }

    private void loadData() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM " + tableName);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            T entity = this.processData(resultSet);
            entities.put(entity.getId(), entity);
        }
    }

    protected abstract T processData(ResultSet resultSet) throws SQLException;

    @Override
    public Optional<T> findOne(ID id) {
        if(id == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        Set<T> allEntities = entities.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }
    @Override
    public Iterable<T> findAll(Sort sort)
    {
        return sort.sort(entities.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toList()));
    }


    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<T> optional = Optional.ofNullable(entities.get(entity.getId()));

        if (optional.isPresent()) {
            return optional;
        }

        this.executeInsert(entity);
        this.entities.put(entity.getId(), entity);

        return Optional.empty();
    }

    protected abstract void executeInsert(T entity);

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<T> optional = Optional.ofNullable(entities.get(id));

        if (!optional.isPresent()) {
            return Optional.empty();
        }
        
        this.executeDelete(id);
        this.entities.remove(id);

        return optional;
    }

    protected abstract void executeDelete(ID id);

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        Optional<T> optional = Optional.ofNullable(entities.get(entity.getId()));
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        
        this.executeUpdate(entity);
        this.entities.replace(entity.getId(), entity);
        
        return optional;
    }

    protected abstract void executeUpdate(T entity);
}
