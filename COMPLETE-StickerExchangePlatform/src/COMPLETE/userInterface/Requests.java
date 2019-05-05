/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Requests {
    private Utils utils = new Utils();
    public Button btnAccept = new Button();
    public Button btnDecline = new Button();
    public Pane iptNumber = utils.createInputField("AnfrageNr:", false);
    public Pane ControlPanel = new HBox(iptNumber, btnAccept, btnDecline);
    public ListView<String> requestList = new ListView<>();

    private Pane createRequestAcceptDeclinePanel() {

        btnAccept.setText("Akzeptieren");
        btnAccept.setId("btnAccept");
        btnDecline.setText("Ablehenen");
        btnDecline.setId("btnDecline");
        btnAccept.setPadding(new Insets(5));
        btnAccept.setStyle("-fx-base: #c1ffdb;");
        btnDecline.setPadding(new Insets(5));
        btnDecline.setStyle("-fx-base: #ffcccc;");

        ControlPanel.setPadding(new Insets(10));

        requestList.setMinHeight(600);
        requestList.setMinWidth(700);

        Pane reqADP = new VBox(ControlPanel, requestList);
        reqADP.setPadding(new Insets(10));
        return reqADP;
    }

    public Pane createRequests() {
        Pane sidebar = utils.createSidebar("Requests");
        Pane functions = createRequestAcceptDeclinePanel();

        Pane requests = new HBox(sidebar, functions);

        return requests;
    }
}
