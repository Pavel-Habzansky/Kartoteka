package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by PavelHabzansky on 02.05.17.
 *
 * Main part of model layer of the application.
 * Person object represents entries in the database.
 */
public class Person {
    private LocalDate birthday;
    private Gender gender;

    private int age;
    private StringProperty nameProperty;
    private StringProperty surnameProperty;
    private StringProperty birthCertificateNumberProperty;
    private LongProperty phoneNumberProperty;

    /**
     * Simple constructor, object is using Property fields
     * @param name of the person
     * @param surname of the person
     * @param birthday of the person
     * @param phoneNumber of the person
     * @param birthCertificateNumber of the person
     * @param gender of the person
     */
    public Person(String name, String surname, LocalDate birthday, long phoneNumber, String birthCertificateNumber, Gender gender){
        this.nameProperty = new SimpleStringProperty(name);
        this.surnameProperty = new SimpleStringProperty(surname);
        this.birthday = birthday;
        this.phoneNumberProperty = new SimpleLongProperty(phoneNumber);
        this.birthCertificateNumberProperty = new SimpleStringProperty(birthCertificateNumber);
        this.gender = gender;
        getAge();
    }

    /**
     * Method for age calculation based on birthday
     * @return int value of age
     */
    public int getAge(){
        this.age = (int)ChronoUnit.YEARS.between(birthday,LocalDate.now());
        return age;
    }

    /**
     * Returns name property of this object
     * @return nameProperty
     */
    public StringProperty getNameProperty(){
        return this.nameProperty;
    }

    /**
     * Returns surname property of this object
     * @return surnameProperty
     */
    public StringProperty getSurnameProperty(){
        return this.surnameProperty;
    }

    /**
     * Returns birth certificate number property of this object
     * @return birthCertificateNumberProperty
     */
    public StringProperty getBirthCertificateNumberProperty(){
        return this.birthCertificateNumberProperty;
    }

    /**
     * Returns phone number property of this object
     * @return phoneNumberProperty
     */
    public LongProperty getPhoneNumberProperty(){
        return this.phoneNumberProperty;
    }

    /**
     * Returns name value of this object
     * @return String name value of this object
     */
    public String getName(){
        return this.nameProperty.get();
    }

    /**
     * Returns surname value of this object
     * @return String surname value of this object
     */
    public String getSurname(){
        return this.surnameProperty.get();
    }

    /**
     * Returns phone number value of this object
     * @return long phone number value
     */
    public long getPhoneNumber(){
        return this.phoneNumberProperty.get();
    }

    /**
     * Returns birth certificate number value of this object
     * @return birth certificate number
     */
    public String getBirthCertificateNumber(){
        return this.birthCertificateNumberProperty.get();
    }

    /**
     * Returns Gender of this object
     * @return Gender
     */
    public Gender getGender(){
        return this.gender;
    }

    /**
     * Returns date of birth od this object
     * @return birthday value as LocalDate
     */
    public LocalDate getBirthday(){
        return this.birthday;
    }

    /**
     * Name setter
     * @param name new name
     */
    public void setName(String name){
        this.nameProperty.setValue(name);
    }

    /**
     * Surname setter
     * @param surname new surname
     */
    public void setSurname(String surname){
        this.surnameProperty.setValue(surname);
    }

    /**
     * Birthday setter
     * @param birthday new birthday
     */
    public void setBirthday(LocalDate birthday){
        this.birthday = birthday;
        getAge();
    }

    /**
     * Phone number setter
     * @param phoneNumber new phone number
     */
    public void setPhoneNumber(long phoneNumber){
        this.phoneNumberProperty.setValue(phoneNumber);
    }

    /**
     * BirthCertificateNumber setter
     * @param certificateNumber new birth certificate number
     */
    public void setBirthCertificateNumber(String certificateNumber){
        this.birthCertificateNumberProperty.setValue(certificateNumber);
    }

    @Override
    public String toString(){
        return "Name: "+getName()+" Surname: "+getSurname()+" Age: "+getAge()+" Birthday: "+getBirthday()+" BirthCertificateNumber: "+getBirthCertificateNumber()+
                " PhoneNumber: "+getPhoneNumber()+" Gender: "+getGender();
    }

}
