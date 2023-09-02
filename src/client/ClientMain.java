package client;

import client.commands.Command;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static client.ClientConnect.connect;
import static client.Inputer.scanner;
import static client.Printer.print;
import static java.lang.String.valueOf;

public class ClientMain {
    public static void main(String[] args) {
        CommandManagerClient commandManager = new CommandManagerClient();
        LinkedHashMap<String, Command> commands = commandManager.getCommands();
        try {
            connect();
            do {
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
                    } else {
                        if (command_list.length == 1) {
                            ResponseSenderClient.sendMessage(command);
                        } else if (command_list.length == 2) {
                            ResponseSenderClient.sendMessWithArg(command_list[0], command_list[1]);
                        }

                    }
                }
                print(RequestReaderClient.serverRead().getMessageName());
            } while (true);
        } catch (RuntimeException e) {
            print("попробуйте позже");

        }
    }
}

