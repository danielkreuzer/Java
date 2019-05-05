/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import COMPLETE.dataStructs.DataHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.xml.crypto.Data;

public class NewAccount {
    private Utils utils = new Utils();
    public Pane iptName = utils.createInputField("Name:", false);
    public Pane iptPassword = utils.createInputField("Passwort:", true);
    public Pane iptPasswordAgain = utils.createInputField("Passwort wiederholen:", true);
    public Pane iptAddress = utils.createInputField("Adresse:", false);
    public Pane iptCountry = utils.createInputField("Land:", false);
    public Pane iptMySticker = utils.createInputField("Meine Sticker:", false);
    public Pane iptDoubleSticker = utils.createInputField("Meine doppelden Sticker:", false);
    public Button btnCreate = new Button();
    public Pane iptRegion;
    ObservableList<String> list;

    public Pane createNewAccount(DataHandler dataHandler) {
        Label lblHeadline = new Label();
        lblHeadline.setFont(new Font("Arial", 25));
        lblHeadline.setText("Neuen Account anlegen");
        lblHeadline.setPadding(new Insets(2, 15, 2, 10));
        list = dataHandler.regions;
        iptRegion = utils.createComboBox("Region:", list);

        btnCreate.setText("Erstellen");
        btnCreate.setPadding(new Insets(5,127,5,127));
        btnCreate.setId("btnCreate");

        Pane newAccount = new VBox(lblHeadline, iptName, iptPassword, iptPasswordAgain, iptAddress, iptCountry, iptRegion, iptMySticker, iptDoubleSticker, btnCreate);
        newAccount.setPadding(new Insets(2));
        return newAccount;
    }
}
