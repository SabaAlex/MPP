package UI;

import model.exceptions.MyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.IClientService;
import services.IMovieService;
import services.IRentalService;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String URL="jdbc:postgresql://localhost:5432/MPP";
    private static final String UserName=System.getProperty("username");
    private static final String Password=System.getProperty("password");
    public static void main(String[] args) throws SQLException {

        try {
            AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(
                            "Config"
                    );

            IClientService clientService = context.getBean(IClientService.class);

            IMovieService movieService = context.getBean(IMovieService.class);

            IRentalService rentalService = context.getBean(IRentalService.class);
            ExecutorService executor= Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors());

            Console console = new Console(clientService, movieService, rentalService,executor);
            console.run();
        }
        catch(MyException e)
        {
            System.out.println("File is corrupted, run aborted with message:"+e.getMessage());
        }
    }
}
