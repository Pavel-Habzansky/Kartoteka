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
    private LocalDate birthday;
    private Gender gender;

    private int age;
    private StringProperty nameProperty;
    private StringProperty surnameProperty;
    private StringProperty birthCertificateNumberProperty;
    private LongProperty phoneNumberProperty;

    public Person(String name, String surname, LocalDate birthday, long phoneNumber, String birthCertificateNumber, Gender gender){
        this.nameProperty = new SimpleStringProperty(name);
        this.surnameProperty = new SimpleStringProperty(surname);
        this.birthday = birthday;
        this.phoneNumberProperty = new SimpleLongProperty(phoneNumber);
        this.birthCertificateNumberProperty = new SimpleStringProperty(birthCertificateNumber);
        this.gender = gender;
        getAge();
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
        return this.nameProperty.get();
    }
    public String getSurname(){
        return this.surnameProperty.get();
    }
    public long getPhoneNumber(){
        return this.phoneNumberProperty.get();
    }
    public String getBirthCertificateNumber(){
        return this.birthCertificateNumberProperty.get();
    }
    public Gender getGender(){
        return this.gender;
    }
    public LocalDate getBirthday(){
        return this.birthday;
    }

    public void setName(String name){
        this.nameProperty.setValue(name);
    }
    public void setSurname(String surname){
        this.surnameProperty.setValue(surname);
    }
    public void setBirthday(LocalDate birthday){
        this.birthday = birthday;
        getAge();
    }
    public void setPhoneNumber(long phoneNumber){
        this.phoneNumberProperty.setValue(phoneNumber);
    }
    public void setBirthCertificateNumber(String certificateNumber){
        this.birthCertificateNumberProperty.setValue(certificateNumber);
    }
    @Override
    public String toString(){
        return "Name: "+getName()+" Surname: "+getSurname()+" Age: "+getAge()+" Birthday: "+getBirthday()+" BirthCertificateNumber: "+getBirthCertificateNumber()+
                " PhoneNumber: "+getPhoneNumber()+" Gender: "+getGender();
    }

}
