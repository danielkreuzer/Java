/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.exchange;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.userInterface.ExchangeManager;
import COMPLETE.userInterface.Utils;

public class ExchangeManagerHandler {
    private Utils utils = new Utils();

    public void HandleExchange(ExchangeManager exchangeManager, DataHandler dataHandler) {
        if(!dataHandler.HandleNewRequest(
                utils.getTextFromInputField(exchangeManager.iptWandet, true),
                utils.getTextFromInputField(exchangeManager.iptGive, true),
                utils.getTextFromInputField(exchangeManager.iptExchangePerson, true))) {
            utils.showStandardDialog("Tauschvorschlag nicht möglich",
                    "Der Tauschpartner existiert nicht oder " +
                            "der Tauschpartner kann nicht mit diesen Stickern tauschen weil er sie selber " +
                            "nicht besitzt | Es kann auch ein Eingabefehler passiert sein");
        } else {
            utils.showStandardDialog("Vorschlag abgesendet", "");
        }
    }
}
