package core.repository.postgreSQL;

import core.model.domain.Client;
import core.repository.postgreSQL.statements.ClientSQLStatements;
import core.model.exceptions.MyException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientSQLRepository extends PostgreSQLRepository<Long, Client> {
    public ClientSQLRepository() throws SQLException {
        super("client");
    }

    @Override
    protected Client processData(ResultSet resultSet) throws SQLException {
        long ID = resultSet.getLong(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        int age = resultSet.getInt(4);

        Client client = new Client(firstName, lastName, age);
        client.setId(ID);

        return client;
    }

    @Override
    protected void executeInsert(Client entity) {
        try(PreparedStatement prep = conn.prepareStatement(ClientSQLStatements.INSERT.composeMessage(super.getTableName()))) {
            prep.setLong(1, entity.getId());
            prep.setString(2, entity.getFirstName());
            prep.setString(3, entity.getLastName());
            prep.setInt(4, entity.getAge());
            prep.executeUpdate();
        }
        catch (SQLException e){
            throw new MyException("SQL Server error occured!");
        }
    }

    @Override
    protected void executeDelete(Long aLong) {
        try(PreparedStatement prep = conn.prepareStatement(ClientSQLStatements.DELETE.composeMessage(super.getTableName()) + aLong)) {
            prep.executeUpdate();
        }
        catch (SQLException e){
            throw new MyException("SQL Server error occured!");
        }
    }

    @Override
    protected void executeUpdate(Client entity) {
        try(PreparedStatement prep = conn.prepareStatement(
                "UPDATE " + super.getTableName() +
                        " SET firstname = " + "'" + entity.getFirstName() + "', " +
                        "lastname = " + "'" + entity.getLastName() + "', " +
                        "age = " + "'" + entity.getAge() + "' " +
                        "WHERE id = " + entity.getId()
                )) {
            prep.executeUpdate();
        }
        catch (SQLException e){
            throw new MyException(e.getMessage());
        }
    }
}
