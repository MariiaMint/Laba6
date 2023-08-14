package client;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class ReadConsole {
    static String command;
    public static String read(Scanner scanner){
        System.out.println("Введите команду (чтобы увидеть справку по командам введите help)");
        try {
            command = scanner.nextLine();
        }catch (NoSuchElementException e){
            System.out.println();
        }
        return command;
    }
}
