

import model.exceptions.MyException;
import services.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by radu.
 */
public class TCPClient {
    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(Message.HOST, Message.PORT);
             var is = socket.getInputStream();
             var os = socket.getOutputStream()
        ) {
            System.out.println("sendAndReceive - sending request: " + request);
            request.writeTo(os);

            System.out.println("sendAndReceive - received response: ");
            Message response = new Message();
            response.readFrom(is);
            System.out.println(response);

            return response;
        } catch (IOException e) {
            throw new MyException("error connection to server " + e.getMessage(), e);
        }
    }
}

