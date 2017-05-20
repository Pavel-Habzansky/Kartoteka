package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.tools.javac.jvm.Gen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Gender;
import model.JsonManager;
import model.Person;
import model.PersonDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        this.tableView.setItems(personObservableList);
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.genderBox.getItems().setAll(Gender.MALE.toString(), Gender.FEMALE.toString());

        logger.debug("Main window Controller initialized");
    }

    public void initObervableList(){
        if (personDB.getDatabase() != null){
            this.personObservableList = FXCollections.observableArrayList(personDB.getDatabase());
        }else {
            this.personObservableList = FXCollections.observableArrayList();
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
