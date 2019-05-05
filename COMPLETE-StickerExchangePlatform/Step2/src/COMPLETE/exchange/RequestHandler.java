/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.exchange;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.userInterface.Requests;
import COMPLETE.userInterface.Utils;

public class RequestHandler {
    private Utils utils = new Utils();

    public void HandleOpenRequests(Requests requests, DataHandler dataHandler) {
        requests.requestList.setItems(dataHandler.getRequestList(dataHandler.actualUser));
    }

    public void HandleAcceptRequest(Requests requests, DataHandler dataHandler) {
        if(!dataHandler.HandleAcceptRequest(
                utils.getTextFromInputField(requests.iptNumber, true)
        )) {
            utils.showStandardDialog("Akzeptieren fehlgeschlagen",
                    "Falls der Tausch nicht mehr möglich ist" +
                            "wurde die Anfrage automatisch gelöscht");
        } else {
            requests.requestList.setItems(dataHandler.getRequestList(dataHandler.actualUser));
        }
    }

    public void HanldeDeclineRequest(Requests requests, DataHandler dataHandler) {
        if(!dataHandler.HandleDeleteRequest(
                utils.getTextFromInputField(requests.iptNumber, true)
        )) {
            utils.showStandardDialog("Entfernen fehlgeschlagen",
                    "Überprüfen Sie Ihre Eingabe");
        } else {
            requests.requestList.setItems(dataHandler.getRequestList(dataHandler.actualUser));
        }
    }

}
