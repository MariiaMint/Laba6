package server.managers.serverTools;

import java.io.DataOutputStream;
import java.io.IOException;

import static client.Printer.print;
import static server.managers.serverTools.ServerConnect.socket;

public class ResponseSender {

    public static void send(String response) {
        try {
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            oos.writeUTF(response);
            oos.flush();
            //oos.close();
            print("response was sent");
        } catch (IOException e) {
            print("ioex responseSender");
        }
    }
}

