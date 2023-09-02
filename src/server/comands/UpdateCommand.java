package server.comands;

import server.managers.CommandExecutor;

public class UpdateCommand implements Command{
    CommandExecutor commandExecutor;
    String description;
    String name;

    public UpdateCommand(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    @Override
    public void execute(String par) {
        commandExecutor.update(par);
    }
    public String description(){
        return(name + ": " + description);
    }
}
