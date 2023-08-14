package client;

import java.io.DataInputStream;
import java.io.IOException;


import static client.ClientConnect.clientSocket;
import static client.Printer.print;

public class ServerRequestReader {
    public static String serverRead() {
        try {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            String serverMessage = in.readUTF();
            print(serverMessage);
            //in.close();

            return serverMessage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}