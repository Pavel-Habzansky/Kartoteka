package model;

import kartoteka.Main;

/**
 * Created by PavelHabzansky on 19.05.17.
 *
 * Part of model layer, enum defines Gender of an entry.
 */
public enum Gender {
    MALE, FEMALE;

    public static Gender getGender(String genderType){
        if (genderType.equals("Zena")){
            return FEMALE;
        }else if (genderType.equals("Muz")){
            return MALE;
        }else {
            return null;
        }
    }

    @Override
    public String toString(){
        switch (this){
            case FEMALE: return "Zena";
            case MALE: return "Muz";
        }
        return "Unknown";
    }
}
