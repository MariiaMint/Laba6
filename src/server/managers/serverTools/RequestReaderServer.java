package server.managers.serverTools;

import generalModule.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

import static client.Printer.print;
import static server.managers.serverTools.ServerConnect.socket;

public class RequestReaderServer {
    public static Message read() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Message clientMessage = (Message) in.readObject();
            print("Получено сообщение от клиента: " + clientMessage.getMessageName());
            return clientMessage;
        } catch (IOException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
