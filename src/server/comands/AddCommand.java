package server.comands;

import generalModule.beginningClasses.HumanBeing;
import server.managers.CommandExecutor;

import static server.managers.serverTools.ResponseSender.send;

public class AddCommand implements  Command{
    CommandExecutor commandExecutor;
    String description;
    String name;
    public AddCommand(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    public void execute(HumanBeing human) {
        commandExecutor.add(human);
    }
    public String description(){
        return(name + ": " + description);
    }
}
