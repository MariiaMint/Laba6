package server.managers.serverTools;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import static client.Printer.print;
import static server.managers.serverTools.ResponseSender.send;
import static server.managers.serverTools.ServerConnect.serverSocket;
import static server.managers.serverTools.ServerConnect.socket;

public class RequestReader {
    public static String read() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String clientMessage = in.readUTF();
            print("Получено сообщение от клиента: " + clientMessage);
            return clientMessage;
            //in.close();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//            String clientMessage = reader.readLine();
//            System.out.println("Получено сообщение от клиента: " + clientMessage);
//
//            reader.close();
//            clientSocket.close();
//
//            return clientMessage;
        } catch (IOException e) {
        }
        return null;
    }
}
