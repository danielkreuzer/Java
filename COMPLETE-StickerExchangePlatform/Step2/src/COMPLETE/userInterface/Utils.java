/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;

import javax.xml.soap.Text;

public class Utils {
    public Pane createInputField(String Name, boolean password) {
        HBox iptField;
        Label label = new Label();
        label.setText(Name);
        label.setMinSize(150,10);
        label.setPadding(new Insets(3,5,0,0));
        if(password) {
            PasswordField textField = new PasswordField();
            iptField = new HBox(label, textField);
        } else {
            TextField textField = new TextField();
            iptField = new HBox(label, textField);
        }
        iptField.setPadding(new Insets(2));
        return iptField;
    }

    public String getSelectedValueFormComboBox(Pane comboBox) {
        try {
            ComboBox combo = (ComboBox) comboBox.getChildren().get(1);
            if (combo.getSelectionModel().getSelectedItem() != null) {
                return combo.getSelectionModel().getSelectedItem().toString();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public String getTextFromInputField(Pane inputPane, boolean delete) {
        TextField inputField = (TextField) inputPane.getChildren().get(1);
        String string = inputField.getText();
        if(delete) {
            inputField.setText("");
        }
        return string;
    }

    public void showStandardDialog(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Panini Tauschbörse WM 2018");
        alert.setHeaderText(header);
        alert.setContentText(text);

        alert.showAndWait();
    }

    public Pane createComboBox(String labelName, ObservableList<String> list) {
        Label label = new Label();
        label.setText(labelName);
        label.setMinSize(150,10);
        label.setPadding(new Insets(3,5,0,0));
        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.setItems(list);
        Pane cmbBox = new HBox(label, comboBox);
        cmbBox.setPadding(new Insets(2));
        return cmbBox;
    }

    public Pane createSidebar(String selected) {
        Button btnHome = new Button();              //0
        btnHome.setId("btnHome");
        Button btnRequest = new Button();           //1
        btnRequest.setId("btnRequest");
        Button btnExchangeManager = new Button();   //2
        btnExchangeManager.setId("btnExchangeManager");
        Button btnProfile = new Button();           //3
        btnProfile.setId("btnProfile");
        Button btnLogout = new Button();            //4
        btnLogout.setId("btnLogout");

        Image imgHome = new Image(getClass().getResourceAsStream("img/home.png"));
        ImageView imageViewHome = new ImageView();
        imageViewHome.setImage(imgHome);
        imageViewHome.setFitHeight(30);
        imageViewHome.setFitWidth(30);
        Image imgExchange = new Image(getClass().getResourceAsStream("img/exchange-alt.png"));
        ImageView imageViewExchange = new ImageView();
        imageViewExchange.setImage(imgExchange);
        imageViewExchange.setFitHeight(30);
        imageViewExchange.setFitWidth(30);
        Image imgBell = new Image(getClass().getResourceAsStream("img/bell.png"));
        ImageView imageViewBell = new ImageView();
        imageViewBell.setImage(imgBell);
        imageViewBell.setFitHeight(30);
        imageViewBell.setFitWidth(30);
        Image imgExclamation = new Image(getClass().getResourceAsStream("img/exclamation.png"));
        Image imgUserCircle = new Image(getClass().getResourceAsStream("img/user-circle.png"));
        ImageView imageViewUser = new ImageView();
        imageViewUser.setImage(imgUserCircle);
        imageViewUser.setFitHeight(30);
        imageViewUser.setFitWidth(30);
        Pane UserImgBox = new HBox(imageViewUser);
        UserImgBox.setPadding(new Insets(10));

        btnHome.setGraphic(imageViewHome);
        btnHome.setText("  HOME");
        btnRequest.setGraphic(imageViewBell);
        btnRequest.setText("  Anfragen");
        btnExchangeManager.setGraphic(imageViewExchange);
        btnExchangeManager.setText("  Tausch vorschlagen");
        btnLogout.setText("Logout");
        btnLogout.setStyle("-fx-base: #ffcccc;");
        btnProfile.setText("Profil");
        btnProfile.setPadding(new Insets(4,145,4,20));
        btnLogout.setPadding(new Insets(4,135,4,20));


        btnHome.setPadding(new Insets(5,140,5,30));
        btnRequest.setPadding(new Insets(5,125,5,30));
        btnExchangeManager.setPadding(new Insets(5,70,5,30));

        switch (selected) {
            case "Home":
                btnHome.setStyle("-fx-base: #b6e7c9;");
                break;
            case "Requests":
                btnRequest.setStyle("-fx-base: #b6e7c9;");
                break;
            case "ExchangeManager":
                btnExchangeManager.setStyle("-fx-base: #b6e7c9;");
                break;
            case "Profile":
                btnProfile.setStyle("-fx-base: #b6e7c9;");
                break;
        }

        Pane btnPaneProfile = new VBox(btnProfile, btnLogout);
        Pane profileManagement = new HBox(UserImgBox, btnPaneProfile);
        profileManagement.setPadding(new Insets(520,0,0,0));

        Line line = new Line(60,0,60,700);

        Pane sidebar = new VBox(btnHome, btnRequest, btnExchangeManager, profileManagement);
        Pane sidebarWithLine = new HBox(sidebar, line);
        return sidebarWithLine;
    }

    public void deleteTextInInputField(Pane inputPane) {
        TextField inputField = (TextField) inputPane.getChildren().get(1);
        inputField.setText("");
    }
    public void clearNewAccount(NewAccount newAccount) {
        deleteTextInInputField(newAccount.iptPasswordAgain);
        deleteTextInInputField(newAccount.iptPassword);
        deleteTextInInputField(newAccount.iptDoubleSticker);
        deleteTextInInputField(newAccount.iptMySticker);
        deleteTextInInputField(newAccount.iptCountry);
        deleteTextInInputField(newAccount.iptAddress);
        deleteTextInInputField(newAccount.iptName);
    }
}
