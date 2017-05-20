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
 */
public class JsonManager {
    private static Logger logger = LogManager.getLogger(JsonManager.class.getName());

    private final Gson gson;

    private static JsonManager jsonManager = new JsonManager();

    private JsonManager(){
        this.gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    }

    public static JsonManager getInstance(){
        return jsonManager;
    }

    public void writeToJson(ArrayList<Person> list,String destination){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(destination))){
            String data = this.gson.toJson(list, new TypeToken<ArrayList<Person>>(){}.getType());
            writer.write(data);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<Person> readFromJson(String source){
        ArrayList<Person> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(source))){
            result = gson.fromJson(reader, new TypeToken<ArrayList<Person>>(){}.getType());
        }catch (IOException e){
            //TODO handle exception properly
            System.err.println(e.getMessage());
        }
        return result;
    }
}