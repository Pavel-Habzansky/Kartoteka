package kartoteka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.JsonManager;
import model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Main extends Application {

    //TODO GSON cant create object based on Properties (e.g. StringProperty, LongProperty etc)
    //TODO probably need to create methods in Person class to create Properties after the Person object is initialized


    private static Logger logger = LogManager.getLogger(Main.class.getName());
//    private JsonManager manager = JsonManager.getInstance();
//    private String destination = "test.json";

    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.debug("Application start");
        Parent root = FXMLLoader.load(getClass().getResource(Constants.MAIN_LAYOUT));
        primaryStage.setTitle("Kartoteka");
        primaryStage.setScene(new Scene(root, 650, 450));
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(450);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
