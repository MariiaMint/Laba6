package client;

import generalModule.beginningClasses.HumanBeing;

import static client.Inputer.scanner;

public class CommandExecutorClient {
    public HumanBeing add() {return HumanBeing.creatingHuman(scanner);
    }
}
