package server;

import client.ClientConnect;
import generalModule.beginningClasses.HumanBeing;
import generalModule.tools.CsvToVector;
import server.managers.CommandExecutor;
import server.managers.CommandManager;
import server.managers.serverTools.RequestReader;
import server.managers.serverTools.ServerConnect;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import static client.Inputer.scanner;
import static client.Printer.print;
import static server.managers.serverTools.ServerConnect.close;
import static server.managers.serverTools.ServerConnect.connect;

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
            String recievedCommand = RequestReader.read();
            String[] command_list;

            if (recievedCommand != null) {
                command_list = recievedCommand.split(" ");
                if (command_list.length == 1) {
                    commandManager.execute(command_list[0], "");
                } else if (command_list.length == 2) {
                    commandManager.execute(command_list[0], command_list[1]);
                }
            }
        }
    }
}
