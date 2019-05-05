/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ExchangeManager {
    private Utils utils = new Utils();
    public Pane iptWandet = utils.createInputField("Gesuchter Sticker:", false);
    public Pane iptGive = utils.createInputField("Tauschen für Sticker:", false);
    public Pane iptExchangePerson = utils.createInputField("Tauschpartner:", false);
    public Button btnSend = new Button();

    public Pane createExchangeManager() {
        btnSend.setText("Absenden");
        btnSend.setPadding(new Insets(5, 125,5,125));
        btnSend.setId("btnSend");

        Pane sidebar = utils.createSidebar("ExchangeManager");
        Pane exmng = new VBox(iptWandet, iptGive, iptExchangePerson, btnSend);
        exmng.setPadding(new Insets(20));
        Pane exmngret = new HBox(sidebar, exmng);
        return exmngret;
    }
}
