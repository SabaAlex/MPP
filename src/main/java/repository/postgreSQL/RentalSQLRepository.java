package repository.postgreSQL;

import model.domain.Rental;
import model.exceptions.MyException;
import repository.postgreSQL.statements.MovieSQLStatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalSQLRepository extends PostgreSQLRepository<Long, Rental> {
    public RentalSQLRepository() throws SQLException {
        super("rental");
    }

    @Override
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
    protected void executeInsert(Rental entity) {
        try(PreparedStatement prep = conn.prepareStatement(MovieSQLStatements.INSERT.composeMessage(super.getTableName()))) {
            prep.setLong(1, entity.getId());
            prep.setLong(2, entity.getClientID());
            prep.setLong(3, entity.getMovieID());
            prep.setInt(4, entity.getDay());
            prep.setInt(5, entity.getMonth());
            prep.setInt(6, entity.getYear());
            prep.executeUpdate();
        }
        catch (SQLException e){
            throw new MyException("SQL Server error occured!");
        }
    }

    @Override
    protected void executeDelete(Long aLong) {
        try(PreparedStatement prep = conn.prepareStatement(MovieSQLStatements.DELETE.composeMessage(super.getTableName()) + aLong)) {
            prep.executeUpdate();
        }
        catch (SQLException e){
            throw new MyException("SQL Server error occured!");
        }
    }

    @Override
    protected void executeUpdate(Rental entity) {
        try(PreparedStatement prep = conn.prepareStatement(
                "UPDATE " + super.getTableName() +
                        " SET clientid = " + "'" + entity.getClientID() + "', " +
                        "movieid = " + "'" + entity.getMovieID() + "', " +
                        "day = " + "'" + entity.getDay() + "', " +
                        "month = " + "'" + entity.getMonth() + "' " +
                        "year = " + "'" + entity.getYear() + "' " +
                        "WHERE id = " + entity.getId()
        )) {
            prep.executeUpdate();
        }
        catch (SQLException e){
            throw new MyException(e.getMessage());
        }
    }
}
