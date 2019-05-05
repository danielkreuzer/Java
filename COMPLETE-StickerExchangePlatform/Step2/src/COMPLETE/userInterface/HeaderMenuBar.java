/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.userInterface;

import COMPLETE.dataStructs.DataHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class HeaderMenuBar {
    private DataHandler dataHandler;

    public HeaderMenuBar(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public MenuBar createMenuBar() {
        MenuItem prefItemLoad = new MenuItem("Laden");


        prefItemLoad.setOnAction(event -> dataHandler.loadFormServer());


        Menu settingsMenu = new Menu("Datei");
        settingsMenu.getItems().add(prefItemLoad);


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(settingsMenu);

        return menuBar;
    }
}
