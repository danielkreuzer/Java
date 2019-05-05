/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.exchange;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.userInterface.UserOverview;
import COMPLETE.userInterface.Utils;

public class UserOverviewHandler {
    private Utils utils = new Utils();
    public void HandleUserOverview(UserOverview userOverview, DataHandler dataHandler) {
        userOverview.partners.setItems(dataHandler.getPartnerList(
                utils.getSelectedValueFormComboBox(userOverview.filterCountry),
                utils.getSelectedValueFormComboBox(userOverview.filterRegion)));
    }
}
