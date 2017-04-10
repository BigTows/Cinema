package ru.bigtows.forms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by bigtows on 10/04/2017.
 */
public class AdminController {
    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        Tab tab = new Tab();
        tabPane.getTabs().add(tab);

    }


}
