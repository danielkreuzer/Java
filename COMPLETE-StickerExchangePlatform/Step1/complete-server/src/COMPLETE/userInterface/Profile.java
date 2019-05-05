/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Profile {
    private Utils utils = new Utils();
    public Pane iptNewSticker = utils.createInputField("Neuer Sticker:", false);
    public Button btnNewSticker = new Button();
    public Pane iptDeleteSticker = utils.createInputField("Sticker löschen", false);
    public Button btnDeleteSticker = new Button();
    public Pane iptOldPasswrd = utils.createInputField("Passwort alt:", true);
    public Pane iptNew1Passwrd = utils.createInputField("Passwort neu:", true);
    public Pane iptNew2Passwrd = utils.createInputField("Passwort neu nochmal:", true);
    public Button btnChangePass = new Button();
    public ListView<String> mySticker = new ListView<>();

    private Pane createPrfileOverview() {


        btnNewSticker.setText("Hinzufügen");
        btnNewSticker.setPadding(new Insets(5,30,5,30));
        btnNewSticker.setId("btnNewSticker");

        btnDeleteSticker.setText("Löschen");
        btnDeleteSticker.setPadding(new Insets(5,30,5,30));
        btnDeleteSticker.setId("btnDeleteSticker");
        Label label = new Label();
        label.setText("Passwort ändern:");

        btnChangePass.setText("Ändern");
        btnChangePass.setPadding(new Insets(5,30,5,30));
        btnChangePass.setId("btnChangePass");
        Label label1 = new Label();
        label1.setText("Meine Sticker:");

        mySticker.setMinWidth(740);


        Pane prfOver = new VBox(iptNewSticker, btnNewSticker, iptDeleteSticker, btnDeleteSticker, label, iptOldPasswrd,
                iptNew1Passwrd, iptNew2Passwrd, btnChangePass, label1, mySticker);
        prfOver.setPadding(new Insets(5));
        return prfOver;
    }

    public Pane createProfileView() {
        Pane function = createPrfileOverview();

        Pane sidebar = utils.createSidebar("Profile");
        Pane prof = new HBox(sidebar, function);
        return prof;
    }
}
