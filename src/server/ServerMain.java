package server;

import generalModule.MessWithArg;
import generalModule.MessWithHuman;
import generalModule.Message;
import generalModule.beginningClasses.HumanBeing;
import generalModule.tools.CsvToVector;
import server.managers.CommandExecutor;
import server.managers.CommandManager;
import server.managers.serverTools.RequestReaderServer;
import server.managers.serverTools.ServerConnect;

import java.io.IOException;
import java.net.SocketException;
import java.util.Vector;

import static client.Inputer.scanner;
import static client.Printer.print;
import static server.managers.serverTools.ServerConnect.*;

public class ServerMain {
    public static void main(String[] args) throws IOException {

        Vector<HumanBeing> collection = new Vector<>();
        String env = "CSV";
        String csvFile = "";
        try {
            csvFile = System.getenv(env);
        } catch (NullPointerException e) {
            print("Переменная окружения CSV задана не корректно, задайте ее и запустите программу заново");
        }


        //парсер csv в Vector
        CsvToVector.csvToVector(csvFile, collection, scanner);


        CommandExecutor commandExecutor = new CommandExecutor(collection, csvFile, scanner);
        CommandManager commandManager = new CommandManager(commandExecutor);
        connect();


        while (true) {
            socket = ServerConnect.serverSocket.accept();
            print("Connection accepted");

            try {
                Message recievedCommand = RequestReaderServer.read();
                String argument = null;
                HumanBeing human = null;

                if (recievedCommand != null) {
                    String commandName = recievedCommand.getMessageName();
                    if (recievedCommand instanceof MessWithArg) {
                        argument = ((MessWithArg) recievedCommand).getArg();
                    }
                    if (recievedCommand instanceof MessWithHuman) {
                        human = ((MessWithHuman) recievedCommand).getHuman();
                    }
                    if (argument == null && human == null) {
                        commandManager.execute(commandName, "");
                    } else if (argument != null) {
                        commandManager.execute(commandName, argument);
                    } else {
                        commandManager.execute(commandName, human);
                    }
                }
            }finally {close();print("клиент отключился");}
        }
    }
}
