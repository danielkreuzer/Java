/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.exchange;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.userInterface.Login;
import COMPLETE.userInterface.Utils;

public class LoginHandler {
    private Utils utils = new Utils();
    public boolean handleLogin(Login login, DataHandler dataHandler) {
        if(dataHandler.CheckLogin(utils.getTextFromInputField(login.iptUsername, true),
                utils.getTextFromInputField(login.iptPassword, true))) {
            return true;
        } else {
            utils.showStandardDialog("Login fehlgeschlagen",
                    "Falscher Benutzername oder Passwort");
            return false;
        }
    }

}
