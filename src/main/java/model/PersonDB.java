package model;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import kartoteka.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by PavelHabzansky on 20.05.17.
 *
 * Part of model layer of application.
 * Object representing database of application, created as Singleton.
 */
public class PersonDB {
    private static Logger logger = LogManager.getLogger(PersonDB.class.getName());

    private static PersonDB personDB = new PersonDB();

    private JsonManager jsonManager = JsonManager.getInstance();

    private ArrayList<Person> database;

    /**
     * Simple constructor, creates ArrayList and load database
     */
    private PersonDB(){
        this.database = new ArrayList<>();
        this.loadDatabase();
    }

    /**
     * Method used for saving the database
     */
    public void deployDatabase(){
        jsonManager.writeToJson(database,Constants.DATABASE_RESOURCE);
    }

    /**
     * Method checking if birth certificate number input is correct
     * @param input birth certificate number
     * @return false if input is incorrect
     */
    public static boolean isBirthCertificateNumberCorrect(String input){
        if (!input.contains("/")){
            logger.error("Birth certificate number doesnt contain /");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Chyba v rodném čísle");
            alert.setContentText("V rodném čísle chybí / ");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return false;
        }
        String[] splitInput = input.split("/+");
        if (splitInput[0].length() != 6){
            logger.error("Wrong birth certificate number input - first part doesnt have 6 digits");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Chyba v rodném čísle");
            alert.setContentText("V první části rodného čísla je špatný počet znaků");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return false;
        }else if ((splitInput[1].length() > 4 || splitInput[1].length() < 3) || Long.parseLong(splitInput[0]+splitInput[1]) % 11 != 0){
            logger.error("Wrong birth certificate number input - input cant be divided by 11");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Chyba v rodném čísle");
            alert.setContentText("Rodné číslo není dělitelné 11ti");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Returns Singleton instance of this object
     * @return instance of this object
     */
    public static PersonDB getInstance(){
        return personDB;
    }

    /**
     * Return database of this object
     * @return ArrayList as database
     */
    public ArrayList<Person> getDatabase(){
        return this.database;
    }

    /**
     * Method loads the database into ArrayList field of this object
     */
    private void loadDatabase(){
        this.database = jsonManager.readFromJson(Constants.DATABASE_RESOURCE);
        if (this.database == null){
            this.database = new ArrayList<>();
        }
    }

    /**
     * Method for printing the whole database
     */
    public void print(){
        for (int i = 0; i < database.size(); i++){
            System.out.println(database.get(i));
        }
    }

    /**
     * Method removes Person from the database
     * @param person to be removed
     */
    public void remove(Person person){
        this.database.remove(person);
    }

    /**
     * Method removes Collection<Person></> from the database
     * @param collection to be removed
     */
    public void removeAll(Collection<Person> collection){
        this.database.removeAll(collection);
    }

    /**
     * Method returns size of the database
     * @return size
     */
    public long size(){
        return this.database.size();
    }

    /**
     * Method adds Person entry to the database
     * @param person to be added
     */
    public void add(Person person){
        this.database.add(person);
    }

    /**
     * Returnd Person on specific index in database
     * @param index of Person
     * @return Person on specific index
     */
    public Person get(int index){
        return this.database.get(index);
    }
}
