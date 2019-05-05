package COMPLETE;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.network.CommunicationModule;
import COMPLETE.userInterface.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

public class CompleteMain extends Application {
    private static final Border DEFAULT_BORDER = new Border(new BorderStroke(Color.DIMGREY, BorderStrokeStyle.SOLID, null, null));

    private Scene scene;
    private Stage actualStage;

    //Network module
    private static CommunicationModule communicationModule = new CommunicationModule();

    //UI Elements
    private ExchangeManager exchangeManager = new ExchangeManager();
    private Login login = new Login();
    private NewAccount newAccount = new NewAccount();
    private Profile profile = new Profile();
    private Requests requests = new Requests();
    private UserOverview userOverview = new UserOverview();

    //Data controller
    private DataHandler dataHandler = new DataHandler();

    //Menu bar
    private HeaderMenuBar headerMenuBar = new HeaderMenuBar(dataHandler);



    private ButtonRouter buttonRouter = new ButtonRouter(exchangeManager, login, newAccount, profile, requests, userOverview, dataHandler);
    public Pane rootPane;

    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(buttonRouter.handleEvent(event)) {
                rootPane = new Pane();
                rootPane = new VBox(headerMenuBar.createMenuBar(), buttonRouter.getNewPane(event));
                scene = new Scene(rootPane, 1000,720);
                actualStage.setScene(scene);
                scene.addEventHandler(ActionEvent.ACTION, new ButtonHandler());
                actualStage.show();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        actualStage = primaryStage;
        rootPane = new VBox(login.createLogin());
        rootPane.setBorder(DEFAULT_BORDER);
        scene = new Scene(rootPane, 1000,700);
        scene.addEventHandler(ActionEvent.ACTION, new ButtonHandler());
        actualStage.setScene(scene);
        actualStage.setTitle("Panini Tauschbörse WM 2018");
        actualStage.show();
    }

    public static void main(String[] args) {
        try {
            Application.launch(args);
        } catch (Exception e) {
            System.out.println("Start failed!");
        }
    }
}
