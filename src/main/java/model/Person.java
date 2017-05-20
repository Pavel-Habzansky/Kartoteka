package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by PavelHabzansky on 02.05.17.
 */
public class Person {

    private String name;
    private String surname;
    private String birthCertificateNumber;
    private long phoneNumber;
    private LocalDate birthday;
    private Gender gender;

    private int age;
    private StringProperty nameProperty;
    private StringProperty surnameProperty;
    private StringProperty birthCertificateNumberProperty;
    private LongProperty phoneNumberProperty;

    public Person(String name, String surname, LocalDate birthday, long phoneNumber, String birthCertificateNumber, Gender gender){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.birthCertificateNumber = birthCertificateNumber;
        this.gender = gender;
        this.birthday = birthday;
        this.age = getAge();

        createProperties();
    }
    private void createProperties(){
        this.nameProperty = new SimpleStringProperty(this.name);
        this.surnameProperty = new SimpleStringProperty(this.surname);
        this.birthCertificateNumberProperty = new SimpleStringProperty(this.birthCertificateNumber);
        this.phoneNumberProperty = new SimpleLongProperty(phoneNumber);
    }

    public int getAge(){
        this.age = (int)ChronoUnit.YEARS.between(birthday,LocalDate.now());
        return age;
    }

    public StringProperty getNameProperty(){
        return this.nameProperty;
    }
    public StringProperty getSurnameProperty(){
        return this.surnameProperty;
    }
    public StringProperty getBirthCertificateNumberProperty(){
        return this.birthCertificateNumberProperty;
    }
    public LongProperty getPhoneNumberProperty(){
        return this.phoneNumberProperty;
    }

    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getBirthCertificateNumber(){
        return this.birthCertificateNumber;
    }
    public Gender getGender(){
        return this.gender;
    }
    public long getPhoneNumber(){
        return this.phoneNumber;
    }
    public LocalDate getBirthday(){
        return this.birthday;
    }

//    @Override
//    public String toString(){
//
//    }

}
