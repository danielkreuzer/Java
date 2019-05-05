/*
 * Copyright (c) 2018 Daniel Kreuzer
 * Mockup1 -> Headerpicture
 */

package COMPLETE.mockups;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Mockup1 extends Application {

    //Default values
    private static final Border DEFAULT_BORDER = new Border(new BorderStroke(Color.DIMGREY, BorderStrokeStyle.SOLID, null, null));

    private HBox createHeader() {
        Image image = new Image(getClass().getResourceAsStream("img/Panini-WM-2018-2.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(250);
        imageView.setFitWidth(500);
        HBox hBox = new HBox(imageView);
        return hBox;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox header = createHeader();
        HBox username = new HBox();
        HBox password = new HBox();

        //Build login Window
        Pane rootPane = new VBox(header, username, password);
        Scene scene = new Scene(rootPane, 500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Panini Tauschbörse WM 2018");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
