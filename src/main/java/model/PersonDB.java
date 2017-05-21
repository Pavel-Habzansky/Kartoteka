package model;

import kartoteka.Constants;

import java.util.ArrayList;

/**
 * Created by PavelHabzansky on 20.05.17.
 */
public class PersonDB {
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
