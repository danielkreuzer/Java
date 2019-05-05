/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.network.CommunicationModule;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class HeaderMenuBar {
    private DataHandler dataHandler;

    public HeaderMenuBar(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public MenuBar createMenuBar() {
        MenuItem prefItemLoad = new MenuItem("Laden");
        MenuItem prefItemSend = new MenuItem("Speichern");

        prefItemLoad.setOnAction(event -> dataHandler.loadFormServer());
        prefItemSend.setOnAction(event -> dataHandler.sendToServer());

        Menu settingsMenu = new Menu("Datei");
        settingsMenu.getItems().add(prefItemLoad);
        settingsMenu.getItems().add(prefItemSend);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(settingsMenu);

        return menuBar;
    }
}
