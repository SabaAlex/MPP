package repository.postgreSQL;

import model.domain.Client;
import model.domain.Movie;
import model.exceptions.MyException;
import repository.postgreSQL.PostgreSQLRepository;
import repository.postgreSQL.statements.MovieSQLStatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieSQLRepository extends PostgreSQLRepository<Long, Movie> {
    public MovieSQLRepository() throws SQLException {
        super("movie");
    }

    @Override
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
    protected void executeInsert(Movie entity) {
        try(PreparedStatement prep = conn.prepareStatement(MovieSQLStatements.INSERT.composeMessage(super.getTableName()))) {
            prep.setLong(1, entity.getId());
            prep.setString(2, entity.getTitle());
            prep.setString(3, entity.getDirector());
            prep.setInt(4, entity.getYearOfRelease());
            prep.setString(5, entity.getMainStar());
            prep.setString(6, entity.getGenre());
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
    protected void executeUpdate(Movie entity) {
        try(PreparedStatement prep = conn.prepareStatement(
                "UPDATE " + super.getTableName() +
                        " SET title = " + "'" + entity.getTitle() + "', " +
                        "director = " + "'" + entity.getDirector() + "', " +
                        "yearofrelease = " + "'" + entity.getYearOfRelease() + "', " +
                        "mainstar = " + "'" + entity.getMainStar() + "' " +
                        "genre = " + "'" + entity.getGenre() + "' " +
                        "WHERE id = " + entity.getId()
        )) {
            prep.executeUpdate();
        }
        catch (SQLException e){
            throw new MyException(e.getMessage());
        }
    }
}
