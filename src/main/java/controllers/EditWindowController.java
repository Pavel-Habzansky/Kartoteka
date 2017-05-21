package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Person;
import model.PersonDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

/**
 * Created by PavelHabzansky on 21.05.17.
 */
public class EditWindowController {
    private static Logger logger = LogManager.getLogger(EditWindowController.class.getName());

    private PersonDB personDB = PersonDB.getInstance();

    private Person editPerson;

    @FXML
    private Label ageLabel;

    @FXML
    private TextField nameEditField;
    @FXML
    private TextField surnameEditField;
    @FXML
    private TextField phoneNumberEditField;
    @FXML
    private TextField birthCertificateNumberEditField;

    @FXML
    private DatePicker bdEditDatePicker;

    @FXML
    public void initialize(){

    }

    public void setEditPerson(Person person){
        logger.debug("Person for editing has been set");
        this.editPerson = person;
        this.nameEditField.setText(editPerson.getName());
        this.surnameEditField.setText(editPerson.getSurname());
        this.phoneNumberEditField.setText(Long.toString(editPerson.getPhoneNumber()));
        this.birthCertificateNumberEditField.setText(editPerson.getBirthCertificateNumber());
        this.bdEditDatePicker.setValue(editPerson.getBirthday());
        this.ageLabel.setText("Vek: "+editPerson.getAge());
    }

    public void handleDoneButton(){
        logger.debug("Button 'Hotov' has been pressed");
        if (nameEditField.getText().isEmpty() || surnameEditField.getText().isEmpty() || birthCertificateNumberEditField.getText().isEmpty() ||
                bdEditDatePicker.getValue() == null || phoneNumberEditField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Špatné parametry");
            alert.setHeaderText("Vynechaný parametr");
            alert.setContentText("Ujistěte se, že jste vyplnili všechna pole");
            alert.showAndWait();
        }else {
            this.personDB.remove(editPerson);
            Person editedPerson;
            String editedName = nameEditField.getText();
            String editedSurname = surnameEditField.getText();
            String editedBirthCertificateNumber = birthCertificateNumberEditField.getText();
            long editedPhoneNumber = Long.parseLong(phoneNumberEditField.getText());
            LocalDate editedBD = bdEditDatePicker.getValue();
            editedPerson = new Person(editedName,editedSurname,editedBD,editedPhoneNumber,editedBirthCertificateNumber,editPerson.getGender());
            this.personDB.add(editedPerson);
            logger.debug(this.editPerson+" has been replaced by "+editedPerson);
            Stage thisWindow = (Stage)this.ageLabel.getScene().getWindow();
            thisWindow.close();
        }

    }

    public void handleCloseButton(){
        Stage thisWindow = (Stage)this.ageLabel.getScene().getWindow();
        thisWindow.close();
    }
}
