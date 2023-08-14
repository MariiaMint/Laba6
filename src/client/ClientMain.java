package client;

import client.commands.Command;

import java.util.LinkedHashMap;

import static client.ClientConnect.connect;
import static client.Inputer.scanner;
import static client.Printer.print;

public class ClientMain {
    public static void main(String[] args) {
        CommandManagerClient commandManager = new CommandManagerClient();
        LinkedHashMap<String, Command> commands = commandManager.getCommands();
        connect();
        while(true){
            String command = ReadConsole.read(scanner);
            String[] command_list;

            if (command != null) {
                command_list = command.split(" ");
                if (commands.containsKey(command_list[0])) {
                    if (command_list.length == 1) {
                        commandManager.execute(command_list[0]);
                    } else if (command_list.length == 2) {
                        commandManager.execute(command_list[0], command_list[1]);
                    }
                }
                else {
                    if (command_list.length == 1) {
                        ResponseSender.send(command);
                    } else if (command_list.length == 2) {
                        ResponseSender.send(command_list[0]/*, command_list[1]*/);
                    }

                }
            }
            ServerRequestReader.serverRead();
        }
    }
}

