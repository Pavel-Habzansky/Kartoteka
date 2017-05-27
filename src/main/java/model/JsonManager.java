package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by PavelHabzansky on 02.05.17.
 *
 * Class created as Singleton, used for writing and reading the database.
 */
public class JsonManager {
    private static Logger logger = LogManager.getLogger(JsonManager.class.getName());

    private final Gson gson;

    private static JsonManager jsonManager = new JsonManager();

    /**
     * Simple private constructor
     */
    private JsonManager(){
        this.gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    }

    /**
     * getInstance() returns singleton instance of this class.
     * @return singleton instance
     */
    public static JsonManager getInstance(){
        return jsonManager;
    }

    /**
     * Method writing to the databse, always saving the whole ArrayList in which all the entries are contained.
     * @param list containing all entries
     * @param destination to where the database is to be saved (database.json is default)
     */
    public void writeToJson(ArrayList<Person> list,String destination){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(destination))){
            String data = this.gson.toJson(list, new TypeToken<ArrayList<Person>>(){}.getType());
            writer.write(data);
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    /**
     * Method reading the database.
     * @param source from which reading should occur
     * @return ArrayList of all entries in the database
     */
    public ArrayList<Person> readFromJson(String source){
        ArrayList<Person> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(source))){
            result = gson.fromJson(reader, new TypeToken<ArrayList<Person>>(){}.getType());
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return result;
    }
}