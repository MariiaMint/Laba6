package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static client.Printer.print;

public class ClientConnect {
    static Socket clientSocket;
    static InetAddress serverAddress;
    static int serverPort;

    public static void connect() {
        try {
            serverAddress = InetAddress.getByName("localhost");
            serverPort = 9000;
            clientSocket = new Socket(serverAddress, serverPort);
            print("Connection accepted");
        } catch (IOException e) {
            System.out.println("Не удалось установить соединение с сервером");
        }
    }

    public static void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть соединение");
        }
    }

    public static OutputStream getOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }
}
