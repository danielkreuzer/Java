/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Login {
    private Utils utils = new Utils();
    public Pane iptUsername = utils.createInputField("Benutzername:", false);
    public Pane iptPassword = utils.createInputField("Passwort:", true);
    public Button btnLogin  = new Button();
    public Button btnNewAccount = new Button();

    private HBox createHeader() {
        Image image = new Image(getClass().getResourceAsStream("img/Panini-WM-2018-2.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(400);
        imageView.setFitWidth(1000);
        HBox hBox = new HBox(imageView);
        return hBox;
    }

    private VBox createLoginPane() {
        btnLogin.setText("Anmelden");
        btnLogin.setPadding(new Insets(5,122,5,122));
        btnLogin.setId("btnLogin");

        Label lblNewAccount = new Label();
        lblNewAccount.setText("Noch keinen Account?");
        lblNewAccount.setPadding(new Insets(5,5,0,0));
        btnNewAccount.setText("Neuen Account anlegen");
        btnNewAccount.setPadding(new Insets(5,10,5,10));
        btnNewAccount.setId("btnNewAccount");
        HBox newAccount = new HBox(lblNewAccount, btnNewAccount);
        newAccount.setPadding(new Insets(30,0,0,0));


        VBox loginPane = new VBox(iptUsername, iptPassword, btnLogin, newAccount);
        loginPane.setPadding(new Insets(70, 0, 70, 370));
        return loginPane;
    }

    public Pane createLogin() {
        Pane header = createHeader();
        Pane body = createLoginPane();
        Pane loginScreen = new VBox(header, body);
        return loginScreen;
    }
}
