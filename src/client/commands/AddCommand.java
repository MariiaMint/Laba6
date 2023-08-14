package client.commands;

import client.CommandExecutorClient;
import server.managers.CommandExecutor;

import java.util.Vector;

public class AddCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public AddCommand(String name){this.name = name;};
    public void execute(String arg){};
    public void execute(){
        executor.add();
    };
}
