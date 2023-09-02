package server.managers;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Vector;

import generalModule.beginningClasses.*;
import server.comands.Command;
import generalModule.tools.HumanComparator;

import static generalModule.beginningClasses.HumanBeing.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import static server.managers.CommandManager.execute;
import static generalModule.tools.FileScanner.scan;
import static generalModule.tools.Validator.*;
import static generalModule.tools.Validator.etoDouble;
import static server.managers.serverTools.ResponseSenderServer.send;
import static server.managers.serverTools.ResponseSenderServer.sendHuman;

public class CommandExecutor {

    static Vector<HumanBeing> collection = new Vector<>();
    String csvFile;
    Scanner scanner;


    public CommandExecutor(Vector collection, String csvFile, Scanner scanner) {
        this.collection = collection;
        this.csvFile = csvFile;
        this.scanner = scanner;
    }

    //HELP
    public void help() {
        StringBuilder sb = new StringBuilder();
        for (Command element : CommandManager.getCommands().values()) {
            sb.append(element.description()+ "\n");
        }
        send(sb.toString());
    }

    // EXIT
    public void exit() {
        send("Завершаемся...");
        scanner.close();
        System.exit(0);
    }

    //ADD
    public void add(HumanBeing human) {
        int id;
        if (collection.isEmpty()) {
            id = 0;
        } else {
            id = 0;
            for (HumanBeing person:collection) {
                id = max(id,person.getId()+1);
            }
        }
        human.setId(id);
        LocalDateTime creationDate = LocalDateTime.now();
        human.setCreationDate(creationDate);
        collection.add(human);
        send("Human added");
    }


    //SAVE
    public void save() {
        try {
            FileWriter fw = new FileWriter(csvFile);
            StringBuilder sb = new StringBuilder();
            // Append strings from array
            for (HumanBeing element : collection) {
                sb.append(element.toCSV());
            }
            fw.write(sb.toString());
            fw.close();

        } catch (IOException | NullPointerException e) {
            send("команда сохранить не может быть выполнена, проверьте, что у файла есть необходимые права доступа");
        }
    }

    //SHOW
    public void show() {
        StringBuilder response = new StringBuilder();
        response.append("id; name; coordinates; creationDate; realHero; hasToothpick; impactSpeed; weaponType; mood; carName; carCool\n");
        for (HumanBeing obj : collection) {
            response.append(obj.toString()+ "\n");
        }
        send(response.toString());
    }

    //remove_first
    public void removeFirst() {
        if (!collection.isEmpty()) {
            collection.removeElementAt(0);
        } else {
            send("коллекция итак пуста");
        }
    }

    //clear
    public void clear() {
        collection.removeAllElements();
    }

    //print_field_descending_mood
    public void printFieldDescendingMood() {
        int apathy = 0;
        int sorrow = 0;
        int sadness = 0;
        for (HumanBeing obj : collection) {
            if (obj.getMood() == Mood.APATHY) {
                apathy += 1;
            } else if (obj.getMood() == Mood.SADNESS) {
                sadness += 1;
            } else if (obj.getMood() == Mood.SORROW) {
                sorrow += 1;
            }
        }
        for (int i = 0; i < sorrow; i++) {
            send("SORROW");
        }
        for (int i = 0; i < sadness; i++) {
            send("SADNESS");
        }
        for (int i = 0; i < apathy; i++) {
            send("APATHY");
        }
    }

    //count_less_than_impact_speed impactSpeed
    public void countLessThanImpactSpeed(String strIs) {
        Double impactSpeed;
        while (true) {
            if (strIs.isBlank()) {
                send("Введите число");
                strIs = scanner.nextLine();
            } else {
                try {
                    impactSpeed = Double.parseDouble(strIs);
                    break;
                } catch (NumberFormatException e) {
                    send("скорость должна быть числом, введите ее правильно");
                    strIs = scanner.nextLine();
                }
            }
        }
        int number = 0;
        for (HumanBeing obj : collection) {
            if (!(obj.getImpactSpeed() == null)) {
                if (obj.getImpactSpeed() < impactSpeed) {
                    number += 1;
                }
            }
        }
        send("количество элементов, значение поля impactSpeed которых меньше заданного равно " + number);
    }

    //removeBId id
    public void removeBId(String strId) {
        boolean removed = false;
        for (HumanBeing obj : collection) {
            if (obj.getId() == parseInt(strId)) {
                collection.removeElement(obj);
                removed = true;
                break;
            }
        }
        if (removed) {
            send("Человек удален");
        } else {
            send("Нет человека с таким id");
        }
    }

    //update id
    public void update(String strId) {
        boolean here = false;
        for (HumanBeing person : collection) {
            if (person.getId() == parseInt(strId)) {
                sendHuman(person);
                here = true;
            }
                List<String> pars = Arrays.asList("name", "coordinates", "realHero", "hasToothpick", "impactSpeed", "weaponType", "mood", "car");
                String par = "";
                while (!par.equals("stop")) {
                    send("выберите что из перечисленного вы хотите изменить(вводите по одному слову параметр)" + pars);
                    send("чтобы закончить изменение введите 'stop'");
                    par = scanner.nextLine();
                    while (!(pars.contains(par)) && !par.equals("stop")) {
                        send("выберите что из перечисленного вы хотите изменить(вводите по одному слову параметр)" + pars);
                        par = scanner.nextLine();
                    }
                    switch (par) {
                        case "name" -> {
                            send("Введите имя человека");
                            person.setName(onlyLetters(scanner));
                        }
                        case "coordinates" -> createCoordinates(person, scanner);
                        case "realHero" -> {
                            send("Является ли человек настоящим героем {yes/no}");
                            boolean realhero = yesNo(scanner);
                            person.setRealHero(realhero);
                        }
                        case "hasToothpick" -> person.setHasToothpick(yesNo(scanner));
                        case "impactSpeed" -> {
                            send("Введите скорость человека(число или пустая строка)");
                            Double impactSpeed = etoDouble(true, scanner);
                            person.setImpactSpeed(impactSpeed);
                        }
                        case "weaponType" -> {
                            send("Каким оружием обладает ваш человек(введите слово из данного списка\n AXE\n HAMMER\n PISTOL\n RIFLE");
                            person.setWeaponType(weaponType(scanner));
                        }
                        case "mood" -> {
                            send("Какое настроение у человека(введите слово из данного списка либо пустую строку)\n SADNESS\n SORROW\n APATHY");
                            person.setMood(mood(scanner));
                        }
                        case "car" -> creatCar(person, scanner);
                    }
                }
            }
        if (!here) {
            send("Человека с таким id нет");
        }
    }

    //info
    public void info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Информация о коллекции:\n");
        sb.append("\tТип: Vector\n");
        sb.append("\tКласс объектов: HumanBeing\n");
        sb.append("\tКоличество элементов: " + collection.size() + "\n");
        if (!collection.isEmpty()) {
            sb.append("\tВремя инициализации: " + collection.firstElement().creationDateToString());
        }
        send(sb.toString());
    }

    //sort
    public void sort() {
        Collections.sort(collection);
    }

    //send_descending
    public void sendDescending() {
        ArrayList<HumanBeing> list = new ArrayList<>(collection);
        list.sort(new HumanComparator());
        send("id; name; coordinates; creationDate; realHero; hasToothpick; impactSpeed; weaponType; mood; carName; carCool");
        for (HumanBeing obj : list) {
            send(obj.toString());
        }
    }

    //add_if_max
    public void addIfMax() {
        send("сравнение идет пунктам realHero, coordinate X, Y, carCool");
        HumanBeing person = creatingHuman( scanner);
        if (!collection.isEmpty()) {
            Vector<HumanBeing> vector = new Vector<>(collection);
            Collections.sort(vector, new HumanComparator());
            Vector<HumanBeing> vector2 = new Vector<>();
            vector2.add(person);
            vector2.add(vector.elementAt(0));
            Collections.sort(vector2, new HumanComparator());
            if (vector2.elementAt(0) == person) {
                collection.add(person);
            } else {
                send("Элемент не является максимальным, мы его не добавили");
            }
        } else {
            collection.add(person);
        }
    }

    //execute_script
    static Vector<String> filePaths = new Vector<>();

    public void executeScript(String arg) {
        ArrayList<String> listCommands = scan(arg);
        filePaths.add(arg);
        System.out.println(filePaths);
        int m = -1;
        int i = -1;
        for (String command : listCommands) {
            m++;
            String[] st = command.split(" ");
            if (i > -1 && m - i < 11) {
                continue;
            }
            if (st[0].equals("add")) {
                i = m;
                try {
                    HumanBeing human = humanForScript(listCommands, i);
                    collection.add(human);
                    send(human.getName() + " добавлен");
                } catch (NumberFormatException e) {
                    send("В файле есть некорректные данные");
                    break;
                }
            } else if (st[0].equals("add_if_max")) {
                i = m;
                HumanBeing human = new HumanBeing();
                try {
                    human = humanForScript(listCommands, i);
                } catch (NumberFormatException e) {
                    send("В файле есть некорректные данные");
                    break;
                }
                if (!collection.isEmpty()) {
                    Vector<HumanBeing> vector = new Vector<>(collection);
                    Collections.sort(vector);
                    Vector<HumanBeing> vector2 = new Vector<>();
                    vector2.add(human);
                    vector2.add(vector.elementAt(vector.size() - 1));
                    Collections.sort(vector2);
                    if (vector2.elementAt(vector2.size() - 1) == human) {
                        collection.add(human);
                    } else {
                        send("Элемент не является максимальным, мы его не добавили");
                    }
                } else {
                    collection.add(human);
                }
            } else if (st[0].equals("update_by_id")) {
                i = m;
                if (st.length == 2 && st[1].chars().allMatch(Character::isDigit)) {
                    HumanBeing human = new HumanBeing();
                    try {
                        human = humanForScript(listCommands, i);
                    } catch (NumberFormatException e) {
                        send("В файле есть некорректные данные");
                        break;
                    }
                    removeBId(st[1]);
                    human.setId(Integer.valueOf(st[1]));
                    collection.add(human);
                } else {
                    send("В файле есть ошибка, исправьте ее");
                    break;
                }
            } else if (st[0].equals("remove_by_id")) {
                if (st.length == 2 && st[1].chars().allMatch(Character::isDigit)) {
                    removeBId(st[1]);
                } else {
                    send("В файле есть ошибка, исправьте ее");
                    break;
                }
            } else {
                if (st.length == 1) {
                    execute(st[0], "");
                } else if (st.length == 2) {
                    if (st[0].equals("execute_script")) {
                        if (filePaths.contains(st[1])) {
                            send("Команда " + st[0] + " " + st[1] + " уже была выполнена, дальнейшее выполнение приведёт к рекурсии");
                        } else {
                            execute(st[0], st[1]);
                        }
                    } else {
                        execute(st[0], st[1]);
                    }
                }
            }
        }
        filePaths.clear();
    }

    private HumanBeing humanForScript(ArrayList<String> listCommands, int i) throws NumberFormatException {
        HumanBeing human = new HumanBeing();
        try {
            int id = 0;
            if (!collection.isEmpty()) {
                for (HumanBeing smn : collection) {
                    id = Integer.max(id, smn.getId() + 1);
                }
            }
            human.setId(id);
            human.setCreationDate(LocalDateTime.now());
            if (listCommands.get(i + 1).chars().allMatch(Character::isLetter)) {
                human.setName(listCommands.get(i + 1));
            } else {
                throw new NumberFormatException();
            }
            human.setCoordinates(new Coordinates(parseDouble(listCommands.get(i + 2).replace(",", ".")), parseDouble(listCommands.get(i + 3).replace(",", "."))));
            human.setRealHero(parseBoolean(listCommands.get(i + 4)));
            human.setHasToothpick(parseBoolean(listCommands.get(i + 5)));
            if (listCommands.get(i + 6).equals("null")) {
                human.setImpactSpeed(null);
            } else {
                human.setImpactSpeed(parseDouble(listCommands.get(i + 6).replace(",", ".")));
            }
            if (listCommands.get(i + 7).equals("null")) {
                human.setWeaponType(null);
            } else {
                human.setWeaponType(WeaponType.valueOf(listCommands.get(i + 7)));
            }
            if (listCommands.get(i + 8).equals("null")) {
                human.setMood(null);
            } else {
                human.setMood(Mood.valueOf(listCommands.get(i + 8)));
            }
            if (!(listCommands.get(i + 9).equals("null")) && listCommands.get(i + 9).chars().allMatch(Character::isLetter)) {
                human.setCar(new Car(listCommands.get(i + 9), parseBoolean(listCommands.get(i + 10))));
            } else if (listCommands.get(i + 9).equals("null")) {
                human.setCar(new Car(null, parseBoolean(listCommands.get(i + 10))));
            } else {
                throw new NumberFormatException();
            }
        } catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
            throw new NumberFormatException();
        }
        return human;
    }
}





