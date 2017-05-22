package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kartoteka.Constants;
import model.Gender;
import model.Person;
import model.PersonDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Controller {
    private static Logger logger = LogManager.getLogger(Controller.class.getName());

    private ObservableList<Person> personObservableList;

    private PersonDB personDB = PersonDB.getInstance();

    @FXML
    private TableView<Person> tableView;

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
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.genderBox.getItems().setAll(Gender.MALE.toString(), Gender.FEMALE.toString());
        tableView.setRowFactory( tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2){
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.EDIT_WINDOW_LAYOUT));
                        Parent root = loader.load();
                        EditWindowController editWindowController = loader.getController();
                        editWindowController.setEditPerson(this.tableView.getSelectionModel().getSelectedItem());
                        Stage stage = new Stage();
                        stage.setMinHeight(Constants.EDIT_WINDOW_MIN_HEIGHT);
                        stage.setMinWidth(Constants.EDIT_WINDOW_MIN_WIDTH);
                        stage.setScene(new Scene(root, Constants.EDIT_WINDOW_MIN_WIDTH,Constants.EDIT_WINDOW_MIN_HEIGHT));
                        stage.showAndWait();
                        initObervableList();
                    }catch (IOException e){
                        logger.error(e.getMessage());
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
        logger.debug("ObservableList has been initialized, TableView is filled");
    }

    @FXML
    public void handleAddButtonOnAction(){
        logger.debug("Add button pressed");
        if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() ||
                genderBox.getSelectionModel().getSelectedItem().isEmpty() || birthCertificateNumberField.getText().isEmpty() ||
                bdDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Špatné parametry");
            alert.setHeaderText("Vynechaný parametr");
            alert.setContentText("Před přidáním se ujistěte, že jste vyplnili všechna pole");
            alert.showAndWait();
        }else {
            if (PersonDB.isBirthCertificateNumberCorrect(this.birthCertificateNumberField.getText())){
                Person newPerson = new Person(nameField.getText(), surnameField.getText(), bdDatePicker.getValue(), Long.parseLong(phoneNumberField.getText()),
                        birthCertificateNumberField.getText(), Gender.getGender(genderBox.getSelectionModel().getSelectedItem()));
                personObservableList.add(newPerson);
                personDB.add(newPerson);
                personDB.deployDatabase();
                logger.debug("New person has been added to the database");
            }else {
                logger.debug("Wrong birth certificate number input");
            }
        }
    }

    @FXML
    public void handleDeleteButtonOnAction(){
        logger.debug("Delete button pressed");
        ObservableList<Person> selected = tableView.getSelectionModel().getSelectedItems();
        personObservableList.removeAll(selected);
    }



}
