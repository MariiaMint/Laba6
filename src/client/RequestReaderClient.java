package client;

import generalModule.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

import static client.ClientConnect.clientSocket;
import static client.Printer.print;

public class RequestReaderClient {
    public static Message serverRead() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            return (Message) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        /*
        try {
            byte[] bytes = new byte[70000];
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            in.readFully(bytes);
            ObjectInputStream ois = new ObjectInputStream(in);
            Message request = (Message) ois.readObject();
            ois.close();
            print(request.getMessageName());
            return request;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
*/
    }
}