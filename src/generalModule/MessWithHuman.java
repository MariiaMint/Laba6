package generalModule;

import generalModule.beginningClasses.HumanBeing;

import java.io.Serializable;

public class MessWithHuman extends Message {
    HumanBeing human;

    public MessWithHuman(String messageName, HumanBeing human) {
        super(messageName);
        this.human = human;
    }

    public HumanBeing getHuman() {
        return human;
    }
}
