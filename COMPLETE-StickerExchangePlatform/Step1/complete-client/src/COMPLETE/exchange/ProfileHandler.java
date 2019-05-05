/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.exchange;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.userInterface.Profile;
import COMPLETE.userInterface.Utils;

public class ProfileHandler {
    Utils utils = new Utils();

    public void HandleShowProfile(Profile profile, DataHandler dataHandler) {
        profile.mySticker.setItems(dataHandler.getMySticker(dataHandler.actualUser));
    }

    public void HandleDeleteSticker(Profile profile, DataHandler dataHandler) {
        if(dataHandler.HandleDeleteSticker(utils.getTextFromInputField(profile.iptDeleteSticker, true))) {
            profile.mySticker.setItems(dataHandler.getMySticker(dataHandler.actualUser));
        } else {
            utils.showStandardDialog("Löschen fehlgeschlagen!"
                    , "Eingabefehler oder Sticker nicht vorhanden");
        }

    }

    public void HandleAddSticker(Profile profile, DataHandler dataHandler) {
        if(dataHandler.HandleNewSticker(utils.getTextFromInputField(profile.iptNewSticker, true))) {
            profile.mySticker.setItems(dataHandler.getMySticker(dataHandler.actualUser));
        } else {
            utils.showStandardDialog("Einfügen fehlgeschlagen!"
                    , "Eingabefehler oder Sticker nicht vorhanden");
        }
    }

    public void HandleChangePass(Profile profile, DataHandler dataHandler) {
        if(!dataHandler.HandleChangePassword(
                utils.getTextFromInputField(profile.iptOldPasswrd, true),
                utils.getTextFromInputField(profile.iptNew1Passwrd, true),
                utils.getTextFromInputField(profile.iptNew2Passwrd, true)
        )) {
            utils.showStandardDialog("Passwort ändern fehlgeschlagen!"
                    , "Eingabe überprüfen");
        }
    }
}
