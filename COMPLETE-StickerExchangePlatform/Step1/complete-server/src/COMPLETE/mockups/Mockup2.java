/*
 * Copyright (c) 2018 Daniel Kreuzer
 * Mockup2 -> Input for Userdata
 */

package COMPLETE.mockups;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Mockup2 extends Application {
    //Default values
    private static final Border DEFAULT_BORDER = new Border(new BorderStroke(Color.DIMGREY, BorderStrokeStyle.SOLID, null, null));
    private Pane createInputField(String Name) {
        Label label = new Label();
        label.setText(Name);
        TextField textField = new TextField();
        HBox iptField = new HBox(label, textField);
        iptField.setBorder(DEFAULT_BORDER);
        return iptField;
    }
    private Pane createUserInput() {
        Pane name = createInputField("Name: ");
        Pane address = createInputField("Adresse: ");
        Pane country = createInputField("Land: ");
        ComboBox<String> region = new ComboBox<String>();
        ObservableList<String> test = FXCollections.observableArrayList();
        test.add("Region 1");
        test.add("Region 2");
        region.setItems(test);
        Pane usrInput = new VBox(name, address, country, region);
        return usrInput;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane usrInput = createUserInput();

        //Build user input window
        Pane rootPane = new VBox(usrInput);
        Scene scene = new Scene(rootPane, 500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Panini Tauschbörse WM 2018");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
