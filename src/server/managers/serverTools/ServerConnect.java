package server.managers.serverTools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static client.Printer.print;

public class ServerConnect {
    public static ServerSocket serverSocket;
    public static Socket socket;

    public static void connect() {
        try {
            serverSocket = new ServerSocket(9000);
            System.out.println("Сервер запущен и ожидает подключений");
            socket = ServerConnect.serverSocket.accept();
            print("Connection accepted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть соединение");
        }
    }
}

