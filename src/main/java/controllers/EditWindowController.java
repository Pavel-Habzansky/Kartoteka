package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import model.PersonDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDate;

/**
 * Created by PavelHabzansky on 21.05.17.
 *
 * Controller class for controlling the edit window.
 */
public class EditWindowController {
    private static Logger logger = LogManager.getLogger(EditWindowController.class.getName());

    private PersonDB personDB = PersonDB.getInstance();

    private Person editPerson;

    @FXML
    private Button doneButton;

    @FXML
    private TextArea textArea;

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
        this.doneButton.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                closeEditWindow();
        });
    }

    /**
     * Method attempting to load description into TextArea if description is found
     */
    public void loadDesc(){
        try(BufferedReader reader = new BufferedReader(new FileReader(editPerson.getName()+editPerson.getSurname()+".txt"))){
            StringBuilder builder = new StringBuilder();
            String desc;
            while ((desc = reader.readLine()) != null)
                builder.append(desc+"\n");
            desc = builder.toString();
            this.textArea.setText(desc);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Nebyl nalezen žádný popis");
            alert.showAndWait();
            logger.error(e.getMessage());
        }
    }

    /**
     * Method which closes this window if needed
     */
    public void closeEditWindow(){
        logger.debug("Edit window closed");
        Stage stage = (Stage) textArea.getScene().getWindow();
        stage.close();
    }

    /**
     * Setting Person to be edited and loading description
     * @param person to be edited
     */
    public void setEditPerson(Person person){
        logger.debug("Person for editing has been set");
        this.editPerson = person;
        this.nameEditField.setText(editPerson.getName());
        this.surnameEditField.setText(editPerson.getSurname());
        this.phoneNumberEditField.setText(Long.toString(editPerson.getPhoneNumber()));
        this.birthCertificateNumberEditField.setText(editPerson.getBirthCertificateNumber());
        this.bdEditDatePicker.setValue(editPerson.getBirthday());
        this.ageLabel.setText("Vek: "+editPerson.getAge());

        loadDesc();
    }

    /**
     * Method for handling "Hotovo" button.
     * Attempts to save new changes and description.
     */
    public void handleDoneButton(){
        logger.debug("Button 'Hotov' has been pressed");
        if (PersonDB.isBirthCertificateNumberCorrect(this.birthCertificateNumberEditField.getText())){
            if (phoneNumberEditField.getText().length() != 9){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chyba!");
                alert.setHeaderText("Chyba v telefonním čísle");
                alert.setContentText("Telefonní číslo nemá správnou délku");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
                return;
            }else if (phoneNumberEditField.getText().contains("-")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chyba!");
                alert.setHeaderText("Chyba v telefonním čísle");
                alert.setContentText("V telefonním čísle se vyskytuje zakázaný -");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
                return;
            }
            if (bdEditDatePicker.getValue().isAfter(LocalDate.now())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chyba!");
                alert.setHeaderText("Chyba v datumu narození");
                alert.setContentText("Datum narození nemůže být v budoucnosti");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
                return;
            }
        if (nameEditField.getText().isEmpty() || surnameEditField.getText().isEmpty() || birthCertificateNumberEditField.getText().isEmpty() ||
                bdEditDatePicker.getValue() == null || phoneNumberEditField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Špatné parametry");
            alert.setHeaderText("Vynechaný parametr");
            alert.setContentText("Ujistěte se, že jste vyplnili všechna pole");
            alert.showAndWait();
        }else {
            if (!textArea.getText().trim().isEmpty()){
                File personDescription = new File(nameEditField.getText()+surnameEditField.getText()+".txt");
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(personDescription))){
                    personDescription.createNewFile();
                    writer.write(textArea.getText());
                }catch (IOException e){
                    logger.error("Failed to save description");
                    logger.error(e.getMessage());
                }
            }
            this.personDB.remove(editPerson);
            this.editPerson.setName(this.nameEditField.getText());
            this.editPerson.setSurname(this.surnameEditField.getText());
            this.editPerson.setBirthCertificateNumber(this.birthCertificateNumberEditField.getText());
            this.editPerson.setBirthday(this.bdEditDatePicker.getValue());
            this.editPerson.setPhoneNumber(Long.parseLong(this.phoneNumberEditField.getText()));
//            Person editedPerson;
//            String editedName = nameEditField.getText();
//            String editedSurname = surnameEditField.getText();
//            String editedBirthCertificateNumber = birthCertificateNumberEditField.getText();
//            long editedPhoneNumber = Long.parseLong(phoneNumberEditField.getText());
//            LocalDate editedBD = bdEditDatePicker.getValue();
//            editedPerson = new Person(editedName,editedSurname,editedBD,editedPhoneNumber,editedBirthCertificateNumber,editPerson.getGender());
            this.personDB.add(editPerson);
            this.personDB.deployDatabase();
            logger.debug(this.editPerson+" has been edited");

            closeEditWindow();
        }
        }
    }

    /**
     * Method handling the "Zavrit" button
     */
    @FXML
    public void handleCloseButton(){
        closeEditWindow();
    }
}
