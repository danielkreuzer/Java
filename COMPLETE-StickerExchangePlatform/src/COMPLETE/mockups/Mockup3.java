/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.mockups;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Mockup3 extends Application {

    private Pane createUserOverview() {
        Button btn1 = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        btn1.setText("Home");
        btn2.setText("Meine Stricker");
        btn3.setText("Tauschassistenten anzeigen");

        ListView<String> stickerList = new ListView<>();
        ObservableList<String> test = FXCollections.observableArrayList();
        test.add("235 - Player XY");
        test.add("300 - Player YX");
        stickerList.setItems(test);
        Pane controlPadLeft = new VBox(btn1, btn2, btn3);
        Pane output = new HBox(controlPadLeft, stickerList);
        return output;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane overview = createUserOverview();


        //Build user overview
        Pane rootPane = new VBox(overview);
        Scene scene = new Scene(rootPane, 500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Panini Tauschbörse WM 2018");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
