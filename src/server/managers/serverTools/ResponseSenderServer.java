package server.managers.serverTools;

import generalModule.MessWithHuman;
import generalModule.Message;
import generalModule.beginningClasses.HumanBeing;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static client.Printer.print;
import static server.managers.serverTools.ServerConnect.socket;

public class ResponseSenderServer {

    public static void send(String response) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message resp = new Message(response);
            oos.writeObject(resp);
            oos.flush();

            print("response was sent" + response);
        } catch (IOException e) {
            print("ioex responseSender");
        }
    }
    public static void sendHuman(HumanBeing human) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            MessWithHuman resp = new MessWithHuman("update", human);
            oos.writeObject(resp);
            oos.flush();

            print("response was sent");
        } catch (IOException e) {
            print("ioex responseSender");
        }
    }
}

