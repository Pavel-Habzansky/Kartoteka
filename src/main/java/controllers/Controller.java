package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kartoteka.Constants;
import model.Gender;
import model.Person;
import model.PersonDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller used for controlling main window of application
 */
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
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Chyba!");
                        alert.setHeaderText("Vyskytla se chyba při načítání");
                        alert.setContentText("Vyskytla se chyba při zobrazování okna");
                        alert.showAndWait();
                        logger.error(e.getMessage());
                    }
                }
            });
            return row;
        });
        setAddEntryShortcut();
        logger.debug("Main window Controller initialized");
    }

    /**
     * Setting shortcut for adding entry.
     * If any field is selected and enter pressed, new entry will be added
     */
    public void setAddEntryShortcut(){
        logger.debug("Shortcut for adding pressed");
        this.nameField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                handleAddButtonOnAction();
        });
        this.surnameField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                handleAddButtonOnAction();
        });
        this.phoneNumberField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                handleAddButtonOnAction();
        });
        this.birthCertificateNumberField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                handleAddButtonOnAction();
        });
        this.genderBox.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                handleAddButtonOnAction();
        });
    }

    /**
     * Initialization of ObservableList.
     * Contents of ObservableList is pulled from PersonDB and forwarded to TableView.
     */
    public void initObervableList(){
        if (personDB.getDatabase() != null){
            this.personObservableList = FXCollections.observableArrayList(personDB.getDatabase());
            this.tableView.setItems(personObservableList);
            this.tableView.refresh();
        }else {
            this.personObservableList = FXCollections.observableArrayList();
            this.tableView.setItems(personObservableList);
            this.tableView.refresh();
        }
        logger.debug("ObservableList has been initialized, TableView is filled");
    }

    /**
     * Method for handling the adding button
     */
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
                if (phoneNumberField.getText().length() != 9){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chyba!");
                    alert.setHeaderText("Chyba v telefonním čísle");
                    alert.setContentText("Telefonní číslo nemá správnou délku");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait();
                    return;
                }else if (phoneNumberField.getText().contains("-")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chyba!");
                    alert.setHeaderText("Chyba v telefonním čísle");
                    alert.setContentText("V telefonním čísle se vyskytuje zakázaný -");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait();
                    return;
                }
                if (bdDatePicker.getValue().isAfter(LocalDate.now())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chyba!");
                    alert.setHeaderText("Chyba v datumu narození");
                    alert.setContentText("Datum narození nemůže být v budoucnosti");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait();
                    return;
                }
                Person newPerson = new Person(nameField.getText(), surnameField.getText(), bdDatePicker.getValue(), Long.parseLong(phoneNumberField.getText()),
                        birthCertificateNumberField.getText(), Gender.getGender(genderBox.getSelectionModel().getSelectedItem()));
                if (isDuplicateEntry(newPerson))
                    return;
                personObservableList.add(newPerson);
                personDB.add(newPerson);
                personDB.deployDatabase();
                logger.trace("New person has been added to the database");
                resetFields();

            }else {
                logger.error("Wrong birth certificate number input");
            }
        }
    }

    /**
     * Method checks for 100 % duplication, if every atribute of a Person is same as the entry Person's
     * true is returned.
     * @param entry to be checked
     * @return true, if duplicate exists
     */
    public boolean isDuplicateEntry(Person entry){
        for (Person person : personObservableList){
            if (person.getAge() == entry.getAge() && person.getGender().equals(entry.getGender()) &&
                    person.getPhoneNumber() == entry.getPhoneNumber() && person.getBirthCertificateNumber().equals(entry.getBirthCertificateNumber())
                    && person.getBirthday().equals(entry.getBirthday()) && person.getName().equals(entry.getName()) && person.getSurname().equals(entry.getSurname())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chyba!");
                alert.setHeaderText("Špatný záznam");
                alert.setContentText("Databáze již obsahuje tento záznam");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    /**
     * Method for reseting input fields after adding new entry.
     */
    public void resetFields(){
        this.bdDatePicker.setValue(null);
        this.nameField.setText(null);
        this.surnameField.setText(null);
        this.phoneNumberField.setText(null);
        this.birthCertificateNumberField.setText(null);
        this.genderBox.getSelectionModel().select(null);
    }

    /**
     * Method for handling the delete button.
     */
    @FXML
    public void handleDeleteButtonOnAction(){
        logger.debug("Delete button pressed");
        ObservableList<Person> selected = tableView.getSelectionModel().getSelectedItems();
        this.tableView.refresh();
        personDB.removeAll(selected);
        personObservableList.removeAll(selected);
        personDB.deployDatabase();
        logger.debug(selected.size()+" entries have been removed");
    }
}
