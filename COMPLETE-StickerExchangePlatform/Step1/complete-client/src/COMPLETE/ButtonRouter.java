/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.exchange.*;
import COMPLETE.userInterface.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


public class ButtonRouter {
    private static ExchangeManager exchangeManager;
    private static Login login;
    private static NewAccount newAccount;
    private static Profile profile;
    private static Requests requests;
    private static UserOverview userOverview;

    private static DataHandler dataHandler;
    private static LoginHandler loginHandler = new LoginHandler();
    private static NewAccountHandler newAccountHandler = new NewAccountHandler();
    private static UserOverviewHandler userOverviewHandler = new UserOverviewHandler();
    private static ExchangeManagerHandler exchangeManagerHandler = new ExchangeManagerHandler();
    private static RequestHandler requestHandler = new RequestHandler();
    private static ProfileHandler profileHandler = new ProfileHandler();

    public ButtonRouter(ExchangeManager exchangeManager, Login login,
                        NewAccount newAccount, Profile profile, Requests requests,
                        UserOverview userOverview,
                        DataHandler dataHandler) {
        this.exchangeManager = exchangeManager;
        this.login = login;
        this.newAccount = newAccount;
        this.profile = profile;
        this.requests = requests;
        this.userOverview = userOverview;
        this.dataHandler = dataHandler;
    }

    public static Pane getNewPane(ActionEvent event) {
        Button button = (Button) event.getTarget();
        String id = button.getId();
        Pane returnPane = new Pane();
        switch (id) {
            case "btnLogin":
                userOverviewHandler.HandleUserOverview(userOverview, dataHandler);
                returnPane = userOverview.createUserOverview(dataHandler);
                break;
            case "btnHome":
                userOverviewHandler.HandleUserOverview(userOverview, dataHandler);
                returnPane = userOverview.createUserOverview(dataHandler);
                break;
            case "btnLogout":
                dataHandler.actualUser = "";
                returnPane = login.createLogin();
                break;
            case "btnRequest":
                requestHandler.HandleOpenRequests(requests, dataHandler);
                returnPane = requests.createRequests();
                break;
            case "btnExchangeManager":
                returnPane = exchangeManager.createExchangeManager();
                break;
            case "btnProfile":
                profileHandler.HandleShowProfile(profile, dataHandler);
                returnPane = profile.createProfileView();
                break;
            case "btnNewAccount":
                returnPane = newAccount.createNewAccount(dataHandler);
                break;
            case "btnCreate":
                userOverviewHandler.HandleUserOverview(userOverview, dataHandler);
                returnPane = userOverview.createUserOverview(dataHandler);
                break;

        }
        return returnPane;
    }

    public static boolean handleEvent(ActionEvent event) {
        if(event.getTarget() instanceof Button) {
            Button button = (Button) event.getTarget();
            if(button.getId() != null) {
                String id = button.getId();
                switch (id) {
                    case "btnHome":
                    case "btnLogout":
                    case "btnRequest":
                    case "btnExchangeManager":
                    case "btnProfile":
                    case "btnNewAccount":
                        return true;
                    case "btnLogin":
                        return loginHandler.handleLogin(login, dataHandler);
                    case "btnCreate":
                        return newAccountHandler.handleNewAccount(newAccount, dataHandler);
                    case "btnApplyFilters":
                        userOverviewHandler.HandleUserOverview(userOverview, dataHandler);
                        return false;
                    case "btnSend":
                        exchangeManagerHandler.HandleExchange(exchangeManager, dataHandler);
                        return false;
                    case "btnAccept":
                        requestHandler.HandleAcceptRequest(requests, dataHandler);
                        return false;
                    case "btnDecline":
                        requestHandler.HanldeDeclineRequest(requests,dataHandler);
                        return false;
                    case "btnNewSticker":
                        profileHandler.HandleAddSticker(profile, dataHandler);
                        return false;
                    case "btnDeleteSticker":
                        profileHandler.HandleDeleteSticker(profile, dataHandler);
                        return false;
                    case "btnChangePass":
                        profileHandler.HandleChangePass(profile, dataHandler);
                        return false;
                    default:
                        return false;
                }
            }
            return false;
        }
        return false;
    }
}
