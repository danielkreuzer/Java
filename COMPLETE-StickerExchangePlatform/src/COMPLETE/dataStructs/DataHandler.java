/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.dataStructs;

import COMPLETE.network.CommunicationModule;
import COMPLETE.userInterface.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.*;
import java.util.function.Predicate;

public class DataHandler {
    private TreeMap<String, User> userTreeMap = new TreeMap<>();
    private TreeMap<Integer, Sticker> baseSticker = new TreeMap<>();
    public ObservableList<String> regions = FXCollections.observableArrayList();
    public String actualUser = "";


    public DataHandler() {

        //Create TestInputs

        //Automatic loading at start
        CommunicationModule communicationModule = new CommunicationModule(this);
        communicationModule.getBaseSticker = true;
        communicationModule.start();

        CommunicationModule communicationModule1 = new CommunicationModule(this);
        communicationModule1.getUserTreeMap = true;
        communicationModule1.start();


        //Create TestRegions
        regions.add("");
        regions.add("Region1");
        regions.add("Region2");

    }

    public void setUserTreeMap(TreeMap<String, User> userTreeMap) {
        this.userTreeMap = userTreeMap;
    }

    public void setBaseSticker(TreeMap<Integer, Sticker> baseSticker) {
        this.baseSticker = baseSticker;
    }

    public boolean CheckLogin(String userName, String password) {
        if(userTreeMap != null) {
            if (userTreeMap.containsKey(userName)) {
                User user = (User) userTreeMap.get(userName);
                if (user.getPassword().equals(password)) {
                    actualUser = user.getName();
                    return true;
                }
                return false;
            }
            return false;
        } else {
            Utils utils = new Utils();
            utils.showStandardDialog("Login fehlgeschlagen",
                    "Netzwerkkommunikation prüfen");
            return false;
        }
    }

    public boolean userContained(String userName) {
        return userTreeMap.containsKey(userName);
    }

    public boolean CheckNewAccount(String Name, String Password, String Password2, String Address,
                                   String Country, String Region,
                                   TreeMap<Integer, Sticker> mySticker,
                                   TreeMap<Integer, Sticker> doubleSticker) {
        loadFormServer();
        if(!Name.equals("") &&
                !Password.equals("") &&
                !Password2.equals("") &&
                Password.equals(Password2) &&
                !Address.equals("") &&
                !Country.equals("") &&
                !Region.equals("")) {
            if(!userTreeMap.containsKey(Name)) {
                User newUser = new User(Name, Password, Address, Country, Region, mySticker, doubleSticker);
                userTreeMap.put(newUser.getName(), newUser);
                CommunicationModule communicationModule = new CommunicationModule();
                communicationModule.addNewUser = true;
                communicationModule.inputUser = newUser;
                communicationModule.start();
                return true;

            }
        }
        return false;
    }


    public  TreeMap<Integer, Sticker> createMyStickerMapOutOfString(String input) {
        try {
            if (!input.equals("")) {
                TreeMap<Integer, Sticker> treeMap = new TreeMap<>();
                String[] arr = input.split(",");
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = arr[i].replace(" ", "");
                    Integer actualNumber = Integer.parseInt(arr[i]);
                    if (baseSticker.containsKey(actualNumber)) {
                        if (treeMap.containsKey(actualNumber)) {
                            //Do nothing already in
                        } else {
                            treeMap.put(actualNumber, baseSticker.get(actualNumber));
                        }
                    } //else ignore number
                }
                return treeMap;
            } else {
                return null;
            }
        }catch (Exception e) {
            return null;
        }
    }

    public  TreeMap<Integer, Sticker> createDoubleStickerMapOutOfString(String input) {
        try {
            if (!input.equals("")) {
                TreeMap<Integer, Sticker> treeMap = new TreeMap<>();
                String[] arr = input.split(",");
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = arr[i].replace(" ", "");
                    Integer actualNumber = Integer.parseInt(arr[i]);
                    if (baseSticker.containsKey(actualNumber)) {
                        if (treeMap.containsKey(actualNumber)) {
                            Integer howOften = treeMap.get(actualNumber).getHowOften();
                            howOften++;
                            treeMap.get(actualNumber).setHowOften(howOften);
                        } else {
                            treeMap.put(actualNumber, baseSticker.get(actualNumber));
                        }
                    } //else ignore number
                }
                return treeMap;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public ObservableList<String> getCountryList() {
        ObservableSet<String> observableSet = FXCollections.observableSet();
        for(Map.Entry<String, User> entry : userTreeMap.entrySet()) {
            observableSet.add(entry.getValue().getCountry());
        }
        ObservableList<String> output = FXCollections.observableArrayList();
        output.add("");
        for(String entry: observableSet) {
            output.add(entry);
        }

        return output;
    }

    private TreeMap<Integer, Sticker> generateUserMissingSticker(String name) {
        TreeMap<Integer, Sticker> treeMap = new TreeMap<>();
        User currentUsr = userTreeMap.get(name);
        for(Map.Entry<Integer, Sticker> entry: baseSticker.entrySet()) {
            if(!currentUsr.getMySticker().containsKey(entry.getKey())) {
                treeMap.put(entry.getKey(), entry.getValue());
            }
        }
        return treeMap;
    }

    private class PartnerListFilter implements Predicate<Map.Entry<Integer, User>> {
        private String country;
        private String region;

        public PartnerListFilter(String country, String region) {
            this.country = country;
            this.region = region;
        }

        @Override
        public boolean test(Map.Entry<Integer, User> user) {
            if(!country.equals("") || !regions.equals("")) {
                if(!country.equals("") && region.equals("")) {
                    return user.getValue().getCountry().equals(country);
                } else if(country.equals("") && !region.equals("")) {
                    return user.getValue().getRegion().equals(region);
                } else {
                    return user.getValue().getCountry().equals(country) && user.getValue().getRegion().equals(region);
                }
            } else {
                return true;
            }
        }
    }

    public ObservableList<String> getPartnerList(String country, String region) {
        HashMap<Integer, User> matchTree = new HashMap<>();
        TreeMap<Integer, Sticker> missingStickers = generateUserMissingSticker(actualUser);
        for(Map.Entry<String, User> entry : userTreeMap.entrySet()) {
            Integer actualMatch = 0;
           if(!entry.getKey().equals(actualUser)) {
               for(Map.Entry<Integer, Sticker> entry1: missingStickers.entrySet()) {
                   if(entry.getValue().getDoubleSticker().containsKey(entry1.getKey())) {
                       actualMatch++;
                   }
               }
               if(!actualMatch.equals(0)) {
                   matchTree.put(actualMatch, entry.getValue());
               }
           }
        }
        HashMap<Integer, User> matchTreeFiltered;
        if(!country.equals("") || !region.equals("")) {
            PartnerListFilter partnerListFilter = new PartnerListFilter(country, region);
            matchTreeFiltered= new HashMap<>();
            for (Map.Entry<Integer, User> entry : matchTree.entrySet()) {
                if (partnerListFilter.test(entry)) {
                    matchTreeFiltered.put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            matchTreeFiltered = matchTree;
        }

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for(Map.Entry<Integer, User> entry : matchTreeFiltered.entrySet()) {
            observableList.add("Übereinstimmung: " + entry.getKey()
                    + " | Person: " + entry.getValue().getName() + " | Bietet: " +
                    entry.getValue().getDoubleSticker().size() + " | Sucht: " +
                    generateUserMissingSticker(entry.getValue().getName()).size());
        }
        return observableList;
    }

    public boolean HandleNewRequest(String StickerWanted,
                                    String StickerGiven,
                                    String User) {
        loadFormServer();
        Integer stkWanted;
        Integer stkGiven;
        User usrExchangePartner;
        User self;
        try {
            stkWanted = Integer.parseInt(StickerWanted);
            stkGiven = Integer.parseInt(StickerGiven);
            usrExchangePartner = userTreeMap.get(User);
            self = userTreeMap.get(actualUser);

            if(usrExchangePartner.getDoubleSticker().containsKey(stkWanted) &&
                    !User.equals(actualUser) &&
                    userTreeMap.get(actualUser).getDoubleSticker().containsKey(stkGiven)) {

                Map.Entry<Sticker, Sticker> stickers = new AbstractMap.SimpleEntry<Sticker, Sticker>(
                        baseSticker.get(stkWanted), baseSticker.get(stkGiven)
                );

                Map.Entry<User, Map.Entry<Sticker, Sticker>> newRequest =
                        new AbstractMap.SimpleEntry<User, Map.Entry<Sticker, Sticker>>(
                                self, stickers
                        );

                usrExchangePartner.getRequestList().add(newRequest);
                CommunicationModule communicationModule = new CommunicationModule();
                communicationModule.addNewRequest = true;
                communicationModule.inputAskedUser = usrExchangePartner.getName();
                communicationModule.inputRequestingUser = actualUser;
                communicationModule.inputWantedSticker = stkWanted;
                communicationModule.inputStickerGiven = stkGiven;
                communicationModule.start();
                return true;

            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean HandleDeleteRequest(String position) {
        loadFormServer();
        try {
            int pos = Integer.parseInt(position);
            CommunicationModule communicationModule = new CommunicationModule();
            communicationModule.deleteRequest = true;
            communicationModule.inputAskedUser = actualUser;
            communicationModule.inputRequestingUser =
                    userTreeMap.get(actualUser).getRequestList().get(pos).getKey().getName();
            communicationModule.inputWantedSticker =
                    userTreeMap.get(actualUser).getRequestList().get(pos).getValue().getKey().getNumber();
            communicationModule.inputStickerGiven =
                    userTreeMap.get(actualUser).getRequestList().get(pos).getValue().getValue().getNumber();
            communicationModule.start();
            userTreeMap.get(actualUser).getRequestList().remove(pos);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public ObservableList<String> getRequestList(String usr) {
        ObservableList<String> requestList = FXCollections.observableArrayList();
        User user = userTreeMap.get(usr);
        int counter = 0;
        for(Map.Entry<User, Map.Entry<Sticker, Sticker>> entry: user.getRequestList()) {
            requestList.add("Anfrage Nr. " + counter +
                    " | Sticker " + entry.getValue().getKey().getNumber() +
                    " gegen Sticker " + entry.getValue().getValue().getNumber());
            counter++;
        }
        return requestList;
    }

    private boolean addSticker(Integer newStickerNumber, String user) {
        loadFormServer();
        if(baseSticker.containsKey(newStickerNumber)){
            if (userTreeMap.get(user).getMySticker().containsKey(newStickerNumber)) {
                if(userTreeMap.get(user).getDoubleSticker().containsKey(newStickerNumber)) {
                    int howOften = userTreeMap.get(user).getDoubleSticker()
                            .get(newStickerNumber).getHowOften();
                    howOften++;
                    userTreeMap.get(user).getDoubleSticker()
                            .get(newStickerNumber).setHowOften(howOften);
                } else {
                    CommunicationModule communicationModule = new CommunicationModule();
                    communicationModule.addDoubleStickerToUser = true;
                    communicationModule.inputUserString = user;
                    communicationModule.inputSticker = newStickerNumber;
                    communicationModule.start();
                    userTreeMap.get(user).getDoubleSticker()
                            .put(newStickerNumber, baseSticker.get(newStickerNumber));
                }
            } else {
                CommunicationModule communicationModule = new CommunicationModule();
                communicationModule.addStickerToUser = true;
                communicationModule.inputUserString = user;
                communicationModule.inputSticker = newStickerNumber;
                communicationModule.start();
                userTreeMap.get(user).getMySticker()
                        .put(newStickerNumber, baseSticker.get(newStickerNumber));
            }
        } else  {
            return false;
        }
        return true;
    }

    private boolean removeSticker(Integer newStickerNumber, String user) {
        loadFormServer();
        if(baseSticker.containsKey(newStickerNumber)){
            if(userTreeMap.get(user).getMySticker().containsKey(newStickerNumber)) {
                if (userTreeMap.get(user).getDoubleSticker().containsKey(newStickerNumber)){
                    if(userTreeMap.get(user).getDoubleSticker().get(newStickerNumber).getHowOften() > 1) {
                        int howOften = userTreeMap.get(user)
                                .getDoubleSticker().get(newStickerNumber).getHowOften();
                        howOften--;
                        userTreeMap.get(user)
                                .getDoubleSticker().get(newStickerNumber).setHowOften(howOften);
                    } else {
                        CommunicationModule communicationModule = new CommunicationModule();
                        communicationModule.deleteDoubleStickerFromUser = true;
                        communicationModule.inputUserString = user;
                        communicationModule.inputSticker = newStickerNumber;
                        communicationModule.start();
                        userTreeMap.get(user).getDoubleSticker().remove(newStickerNumber);
                    }
                    return true;
                }
                CommunicationModule communicationModule = new CommunicationModule();
                communicationModule.deleteStickerFromUser = true;
                communicationModule.inputUserString = user;
                communicationModule.inputSticker = newStickerNumber;
                communicationModule.start();
                userTreeMap.get(user).getMySticker().remove(newStickerNumber);
                return true;
            } else {
                return false;
            }
        } else  {
            return false;
        }
    }

    public boolean HandleAcceptRequest(String position) {
        loadFormServer();
        try {
            int pos = Integer.parseInt(position);
            Map.Entry<User, Map.Entry<Sticker, Sticker>> entry =
                    userTreeMap.get(actualUser).getRequestList().get(pos);
            //wanted / given
            if(userTreeMap.get(actualUser).getDoubleSticker().containsKey(
                    entry.getValue().getKey().getNumber()
            )) {
                boolean output =
                removeSticker(entry.getValue().getKey().getNumber(), actualUser) &&
                addSticker(entry.getValue().getValue().getNumber(), actualUser) &&
                removeSticker(entry.getValue().getValue().getNumber(), entry.getKey().getName()) &&
                addSticker(entry.getValue().getKey().getNumber(), entry.getKey().getName());
                if(output)
                HandleDeleteRequest(position);
                return output;
            } else {
                //Cant exchange sticker
                //Sticker not available
                HandleDeleteRequest(position);
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean HandleNewSticker(String newSticker) {
        try {
            int newStickerNumber = Integer.parseInt(newSticker);
            return addSticker(newStickerNumber, actualUser);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean HandleDeleteSticker(String deleteSticker) {
        try {
            int newStickerNumber = Integer.parseInt(deleteSticker);
            return removeSticker(newStickerNumber, actualUser);
        } catch (Exception e) {
            return false;
        }
    }

    public ObservableList<String> getMySticker(String user) {
        ObservableList<String> mySticker = FXCollections.observableArrayList();
        try {
            for(Map.Entry<Integer, Sticker> entry: userTreeMap.get(user).getMySticker().entrySet()) {
                String output = "Nummer: " + entry.getValue().getNumber() +
                        " | Beschreibung: " + entry.getValue().getDescription();

                if(userTreeMap.get(user).getDoubleSticker().containsKey(
                        entry.getValue().getNumber()
                )) {
                    output += " | Doppeld: " +
                            userTreeMap.get(user).getDoubleSticker().get(entry.getValue().getNumber()).getHowOften();
                }
                mySticker.add(output);
            }
        } catch (Exception e) {
            //do nothing
        }
        return mySticker;
    }

    public boolean HandleChangePassword(String inputOld, String inputNew1, String inputNew2) {
        loadFormServer();
        if(inputOld.equals(userTreeMap.get(actualUser).getPassword())
                && inputNew1.equals(inputNew2) &&
                !inputNew1.equals("") &&
                !inputNew2.equals("")) {
            CommunicationModule communicationModule = new CommunicationModule();
            communicationModule.updatePassword = true;
            communicationModule.inputUserString = actualUser;
            communicationModule.inputNewPassword = inputNew1;
            communicationModule.start();
            userTreeMap.get(actualUser).setPassword(inputNew1);
            return true;
        }
        return false;
    }

    public void loadFormServer() {
       CommunicationModule communicationModule = new CommunicationModule(this);
       communicationModule.getUserTreeMap = true;
       communicationModule.start();

       CommunicationModule communicationModule1 = new CommunicationModule(this);
       communicationModule.getBaseSticker = true;
       communicationModule1.start();
    }
}
