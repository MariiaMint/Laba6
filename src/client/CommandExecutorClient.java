package client;

import generalModule.beginningClasses.HumanBeing;
import generalModule.tools.Validator;

import static client.Inputer.scanner;
import static client.Printer.print;
import static client.ReadConsole.read;
import static client.ResponseSenderClient.sendMessWithArg;
import static client.ResponseSenderClient.sendMessWithHuman;
import static generalModule.tools.Validator.id;
import static java.lang.String.valueOf;

public class CommandExecutorClient {
    public void add() {sendMessWithHuman("add",HumanBeing.creatingHuman(scanner));
    }
    public void remove_by_id(String strId){
        while (strId == null) {
            strId = id("", scanner);
        }
            sendMessWithArg("remove_by_id", strId);
    }
    public void update(String strId){
        strId = id("",scanner);
        sendMessWithArg("update", strId);
    }
}
