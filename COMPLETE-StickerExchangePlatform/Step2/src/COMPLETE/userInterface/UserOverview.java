/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.userInterface.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class UserOverview {
    private Utils utils = new Utils();
    public Pane filterCountry;
    public Pane filterRegion;
    public ListView<String> partners = new ListView<>();
    public Button btnApplyFilters = new Button();

    private Pane createExchangePartnerList(DataHandler dataHandler) {
        ObservableList<String> regionList = dataHandler.regions;
        filterRegion = utils.createComboBox("Region:", regionList);

        ObservableList<String> countryList = dataHandler.getCountryList();
        filterCountry = utils.createComboBox("Land:", countryList);
        btnApplyFilters.setId("btnApplyFilters");
        btnApplyFilters.setText("Filter anwenden");
        btnApplyFilters.setPadding(new Insets(5,10,5,10));
        HBox filters = new HBox(filterCountry, filterRegion, btnApplyFilters);

        partners.setMinWidth(740);
        partners.setMinHeight(650);

        Pane exchangePartnerList = new VBox(filters, partners);
        exchangePartnerList.setPadding(new Insets(5));
        exchangePartnerList.setMinWidth(740);
        exchangePartnerList.setMinHeight(700);
        return exchangePartnerList;
    }

    public Pane createUserOverview(DataHandler dataHandler) {
        Pane sidebar = utils.createSidebar("Home");
        Pane functions = createExchangePartnerList(dataHandler);
        Pane userOverview = new HBox(sidebar,functions);
        return userOverview;
    }


}
