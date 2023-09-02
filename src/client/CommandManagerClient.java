package client;


import client.commands.AddCommand;
import client.commands.Command;
import client.commands.RemoveByIdCommand;

import java.util.LinkedHashMap;

import static client.Printer.print;

public class CommandManagerClient {
    private static LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
    public CommandManagerClient(){
        commands.put("remove_by_id", new RemoveByIdCommand("remove_by_id"));
        commands.put("add", new AddCommand("add"));
    }
    public LinkedHashMap<String, Command> getCommands(){return commands;}

    public void execute(String name){
        try {
            commands.get(name).execute();
        }catch (NullPointerException e){print("Данной команды не найдено");}
    }
    public void execute(String name, String arg){
        try {
            commands.get(name).execute(arg);
        }catch (NullPointerException e){print("Данной команды не найдено");}
    }
}
