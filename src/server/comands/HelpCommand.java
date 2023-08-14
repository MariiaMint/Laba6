package server.comands;

import server.managers.CommandExecutor;

import static server.managers.serverTools.ResponseSender.send;

public class HelpCommand implements Command {
    CommandExecutor commandExecutor;
    String description;
    String name;

    public HelpCommand(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    @Override
    public void execute(String par) {
        commandExecutor.help();
    }

    public String description() {
        return(name + ": " + description);
    }
}

