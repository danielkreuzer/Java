/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.dataStructs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class User implements Serializable {
    private String Name;
    private String Password;
    private String Address;
    private String Country;
    private String Region;
    private TreeMap<Integer, Sticker> mySticker;
    private TreeMap<Integer, Sticker> doubleSticker;
    private List<Map.Entry<User, Map.Entry<Sticker, Sticker>>> requestList = new ArrayList<>();

    public User() {

    }

    public User(String name,
                String password,
                String address,
                String country, String region, TreeMap<Integer, Sticker> mySticker,
                TreeMap<Integer, Sticker> doubleSticker) {
        Name = name;
        Address = address;
        this.Password = password;
        Country = country;
        Region = region;
        this.mySticker = mySticker;
        this.doubleSticker = doubleSticker;
    }

    public List<Map.Entry<User, Map.Entry<Sticker, Sticker>>> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Map.Entry<User, Map.Entry<Sticker, Sticker>>> requestList) {
        this.requestList = requestList;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public TreeMap<Integer, Sticker> getMySticker() {
        return mySticker;
    }

    public void setMySticker(TreeMap<Integer, Sticker> mySticker) {
        this.mySticker = mySticker;
    }

    public TreeMap<Integer, Sticker> getDoubleSticker() {
        return doubleSticker;
    }

    public void setDoubleSticker(TreeMap<Integer, Sticker> doubleSticker) {
        this.doubleSticker = doubleSticker;
    }
}
