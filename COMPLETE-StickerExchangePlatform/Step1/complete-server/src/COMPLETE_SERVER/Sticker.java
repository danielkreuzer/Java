/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE_SERVER;

import java.io.Serializable;

public class Sticker implements Serializable {
    private int Number;
    private String Sector;
    private String Description;
    private int HowOften = 1;

    public Sticker(int number, String sector, String description) {
        Number = number;
        Sector = sector;
        Description = description;
    }

    public int getHowOften() {
        return HowOften;
    }

    public void setHowOften(int howOften) {
        HowOften = howOften;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
