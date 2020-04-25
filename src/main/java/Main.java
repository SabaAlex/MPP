import Service.ClientService;
import Service.MovieService;
import Service.RentalService;
import UI.Console;
import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import model.validators.RentalValidator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.*;
import repository.file.ClientFileRepository;
import repository.file.MovieFileRepository;
import repository.file.RentalFileRepository;
import repository.postgreSQL.ClientSQLRepository;
import repository.postgreSQL.MovieSQLRepository;
import repository.postgreSQL.RentalSQLRepository;
import repository.xml.ClientXMLRepository;
import repository.xml.MovieXMLRepository;
import repository.xml.RentalXMLRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "./"
                );
        context.getBean(Console.class).run();

    }
}
