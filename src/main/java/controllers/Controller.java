package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.tools.javac.jvm.Gen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kartoteka.Constants;
import model.Gender;
import model.JsonManager;
import model.Person;
import model.PersonDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;

public class Controller {
    private static Logger logger = LogManager.getLogger(Controller.class.getName());

    private ObservableList<Person> personObservableList;

    private JsonManager jsonManager = JsonManager.getInstance();

    private PersonDB personDB = PersonDB.getInstance();

    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> surnameColumn;
    @FXML
    private TableColumn<Person, String> birthCertificateNumberColumn;
    @FXML
    private TableColumn<Person, String> genderColumn;
    @FXML
    private TableColumn<Person, Date> birthdayColumn;

    @FXML
    private TextField birthCertificateNumberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ComboBox<String> genderBox;
    @FXML
    private DatePicker bdDatePicker;

    @FXML
    public void initialize(){
        initObervableList();
//        this.tableView.setItems(personObservableList);
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.genderBox.getItems().setAll(Gender.MALE.toString(), Gender.FEMALE.toString());
        tableView.setRowFactory( tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2){
                    //TODO implement edit window
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.EDIT_WINDOW_LAYOUT));
                        Parent root = loader.load();
                        EditWindowController editWindowController = loader.getController();
                        editWindowController.setEditPerson(this.tableView.getSelectionModel().getSelectedItem());
                        Stage stage = new Stage();
                        stage.setMinHeight(400);
                        stage.setMinWidth(500);
                        stage.setScene(new Scene(root, 500,400));
                        stage.showAndWait();
                        initObervableList();
                    }catch (IOException e){
                        //TODO handle exception
                    }


                }
            });
            return row;
        });

        logger.debug("Main window Controller initialized");
    }

    public void initObervableList(){
        if (personDB.getDatabase() != null){
            this.personObservableList = FXCollections.observableArrayList(personDB.getDatabase());
            this.tableView.setItems(personObservableList);
        }else {
            this.personObservableList = FXCollections.observableArrayList();
            this.tableView.setItems(personObservableList);
        }

    }

    @FXML
    public void handleAddButtonOnAction(){
        if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() ||
                genderBox.getSelectionModel().getSelectedItem().isEmpty() || birthCertificateNumberField.getText().isEmpty() ||
                bdDatePicker.getValue() == null){
            //TODO notify user with dialog window about empty TextFields

        }else {
            Person newPerson = new Person(nameField.getText(), surnameField.getText(), bdDatePicker.getValue(), Long.parseLong(phoneNumberField.getText()),
                    birthCertificateNumberField.getText(), Gender.getGender(genderBox.getSelectionModel().getSelectedItem()));
            personObservableList.add(newPerson);
            personDB.add(newPerson);
            personDB.deployDatabase();
        }
    }

    @FXML
    public void handleDeleteButtonOnAction(){
        logger.debug("Delete button pressed");
        ObservableList<Person> selected = tableView.getSelectionModel().getSelectedItems();
        personObservableList.removeAll(selected);
    }



}
