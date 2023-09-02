package client.commands;

import client.CommandExecutorClient;

public class UpdateCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public UpdateCommand(String name){this.name = name;};
    public void execute(String arg){executor.update(arg);};

    public void execute(){
        executor.update(null);
    }
}
