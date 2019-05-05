/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.exchange;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.dataStructs.Sticker;
import COMPLETE.userInterface.NewAccount;
import COMPLETE.userInterface.Utils;

import java.util.TreeMap;

public class NewAccountHandler {
    private Utils utils = new Utils();
    public boolean handleNewAccount(NewAccount newAccount, DataHandler dataHandler) {
        String Name = utils.getTextFromInputField(newAccount.iptName, false);
        String Password = utils.getTextFromInputField(newAccount.iptPassword, false);
        String Password2 = utils.getTextFromInputField(newAccount.iptPasswordAgain, false);
        String Address = utils.getTextFromInputField(newAccount.iptAddress, false);
        String Country = utils.getTextFromInputField(newAccount.iptCountry, false);
        String Region = utils.getSelectedValueFormComboBox(newAccount.iptRegion);
        String iptMySticker =  utils.getTextFromInputField(newAccount.iptMySticker, false);
        String iptDoubleSticker = utils.getTextFromInputField(newAccount.iptDoubleSticker, false);
        TreeMap<Integer, Sticker> mySticker = dataHandler.createMyStickerMapOutOfString(iptMySticker);
        TreeMap<Integer, Sticker> doubleSticker = dataHandler.createDoubleStickerMapOutOfString(iptDoubleSticker);
        if(!dataHandler.userContained(Name)) {
            if(dataHandler.CheckNewAccount(Name, Password, Password2, Address, Country, Region, mySticker, doubleSticker)) {
                dataHandler.actualUser = Name;
                utils.clearNewAccount(newAccount);
                return true;
            }
        }
        utils.showStandardDialog("Neuen Account erstellen fehlgeschlagen",
                "Überprüfen Sie bitte Ihre eingaben");
        return false;
    }
}
