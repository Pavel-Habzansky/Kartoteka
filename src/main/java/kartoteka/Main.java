package kartoteka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {

    private static Logger logger = LogManager.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.debug("Application start");
        Parent root = FXMLLoader.load(getClass().getResource(Constants.MAIN_LAYOUT));
        primaryStage.setTitle("Kartoteka");
        primaryStage.setScene(new Scene(root, Constants.MAIN_WINDOW_MIN_WIDTH, Constants.MAIN_WINDOW_MIN_HEIGHT));
        primaryStage.setMinWidth(Constants.MAIN_WINDOW_MIN_WIDTH);
        primaryStage.setMinHeight(Constants.MAIN_WINDOW_MIN_HEIGHT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
