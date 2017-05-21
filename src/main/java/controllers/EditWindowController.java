package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Person;
import model.PersonDB;

import java.time.LocalDate;

/**
 * Created by PavelHabzansky on 21.05.17.
 */
public class EditWindowController {

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
        this.editPerson = person;
        this.nameEditField.setText(editPerson.getName());
        this.surnameEditField.setText(editPerson.getSurname());
        this.phoneNumberEditField.setText(Long.toString(editPerson.getPhoneNumber()));
        this.birthCertificateNumberEditField.setText(editPerson.getBirthCertificateNumber());
        this.bdEditDatePicker.setValue(editPerson.getBirthday());
        this.ageLabel.setText("Vek: "+editPerson.getAge());
    }

    public void handleDoneButton(){
        this.personDB.remove(editPerson);
        Person editedPerson;
        String editedName = nameEditField.getText();
        String editedSurname = surnameEditField.getText();
        String editedBirthCertificateNumber = birthCertificateNumberEditField.getText();
        long editedPhoneNumber = Long.parseLong(phoneNumberEditField.getText());
        LocalDate editedBD = bdEditDatePicker.getValue();
//        this.editPerson.setName(nameEditField.getText());
//        this.editPerson.setSurname(surnameEditField.getText());
//        this.editPerson.setPhoneNumber(Long.parseLong(phoneNumberEditField.getText()));
//        this.editPerson.setBirthCertificateNumber(birthCertificateNumberEditField.getText());
//        this.editPerson.setBirthday(bdEditDatePicker.getValue());
        editedPerson = new Person(editedName,editedSurname,editedBD,editedPhoneNumber,editedBirthCertificateNumber,editPerson.getGender());
        this.personDB.add(editedPerson);
    }
}
