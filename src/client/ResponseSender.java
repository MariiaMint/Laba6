package client;

import java.io.DataOutputStream;
import java.io.IOException;

import static client.ClientConnect.clientSocket;
import static client.Printer.print;

public class ResponseSender {
    public static void send(String response) {
        try {
            DataOutputStream oos = new DataOutputStream(clientSocket.getOutputStream());
            oos.writeUTF(response);
            oos.flush();
            //oos.close();
            print("command was sent");
        } catch (IOException e) {
            print("ioex responseSender");
        }
    }
}
//        try {
//            OutputStream outputStream = ClientConnect.getOutputStream();
//            byte[] data = response.getBytes();
//            outputStream.write(data);
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static void send(String response, String arg) {
//        try {
//            OutputStream outputStream = ClientConnect.getOutputStream();
//            byte[] data = response.getBytes();
//            outputStream.write(data);
//            outputStream.flush();
//            byte[] data2 = arg.getBytes();
//            outputStream.write(data2);
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void sendObject(byte[] response) {
//        try {
//            OutputStream outputStream = ClientConnect.getOutputStream();
//            outputStream.write(response);
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
