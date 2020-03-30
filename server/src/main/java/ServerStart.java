import model.domain.Client;
import model.domain.utils.FactorySerializable;
import model.exceptions.MyException;
import model.validators.ClientValidator;
import repository.IRepository;
import repository.postgreSQL.ClientSQLRepository;
import services.ClientService;
import services.IService;
import services.Message;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public class ServerStart {

    public static void main(String[] args) {
        try {

            System.out.println("server started");
            ExecutorService executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors()
            );
            try {
                ClientValidator clientValidator = new ClientValidator();
                IRepository<Long, Client> ClientRepository = new ClientSQLRepository();
                ClientService clientService = new ClientService(ClientRepository, clientValidator, executorService);

                TCPServer tcpServer = new TCPServer(executorService);

                tcpServer.addHandler(ClientService.Commands.ADD_ENTITY.getCmdMessage(), (request) -> {
                    Client client = FactorySerializable.createClient(request.getBody());
                    Future<Client> future = clientService.addEntity(client);
                    try {
                        Client result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

//        tcpServer.addHandler(HelloService.SAY_BYE, (request) -> {
//            String name = request.getBody();
//            Future<String> future = helloService.sayBye(name);
//            try {
//                String result = future.get();
//                return new Message("ok", result);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//                return new Message("error", e.getMessage());
//            }
//        });

                tcpServer.startServer();

                executorService.shutdown();
            } catch (RuntimeException | SQLException ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}