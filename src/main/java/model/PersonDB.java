package model;

import javafx.scene.control.Alert;
import kartoteka.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by PavelHabzansky on 20.05.17.
 */
public class PersonDB {
    private static Logger logger = LogManager.getLogger(PersonDB.class.getName());

    private static PersonDB personDB = new PersonDB();

    private JsonManager jsonManager = JsonManager.getInstance();

    private ArrayList<Person> database;

    private PersonDB(){
        this.database = new ArrayList<>();
        this.loadDatabase();
    }

    public void deployDatabase(){
        jsonManager.writeToJson(database,Constants.DATABASE_RESOURCE);
    }

    public static boolean isBirthCertificateNumberCorrect(String input){
        if (!input.contains("/")){
            logger.error("Birth certificate number doesnt contain /");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Chyba v rodném čísle");
            alert.setContentText("V rodném čísle chybí / ");
            return false;
        }
        String[] splitInput = input.split("/+");
        if (splitInput[0].length() != 6){
            logger.error("Wrong birth certificate number input - first part doesnt have 6 digits");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Chyba v rodném čísle");
            alert.setContentText("V první části rodného čísla je špatný počet znaků");
            return false;
        }else if ((splitInput[1].length() > 4 || splitInput[1].length() < 3) || Integer.parseInt(splitInput[1]) % 11 != 0){
            logger.error("Wrong birth certificate number input - second part cant be divided by 11");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Chyba v rodném čísle");
            alert.setContentText("Druhá část rodného čísla není dělitelná 11ti");
            return false;
        }
        return true;
    }

    public static PersonDB getInstance(){
        return personDB;
    }

    public ArrayList<Person> getDatabase(){
        return this.database;
    }

    private void loadDatabase(){
        this.database = jsonManager.readFromJson(Constants.DATABASE_RESOURCE);
        if (this.database == null){
            this.database = new ArrayList<>();
        }
    }

    public void print(){
        for (int i = 0; i < database.size(); i++){
            System.out.println(database.get(i));
        }
    }

    public void remove(Person person){
        this.database.remove(person);
    }

    public long size(){
        return this.database.size();
    }

    public void add(Person person){
        this.database.add(person);
    }

    public Person get(int index){
        return this.database.get(index);
    }
}
