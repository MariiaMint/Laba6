package server.comands;

import generalModule.beginningClasses.HumanBeing;

import java.io.IOException;

public interface Command {
    default void execute(String par) throws IOException{}
    default void execute(HumanBeing human) throws IOException{}

    default void execute() throws IOException{}
    String description();
}
