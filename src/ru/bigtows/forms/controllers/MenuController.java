package ru.bigtows.forms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import ru.bigtows.Main;
import ru.bigtows.util.Debug;

import java.sql.SQLException;

/**
 * Created by bigtows on 19/03/2017.
 */
public class MenuController {


    @FXML
    private ListView listtables;
    @FXML
    private TableView table;

    @FXML
    public void initialize() {
        try {
            listtables.setItems(Main.db.getTables());
        } catch (SQLException e) {
            Debug.log("[Form Menu]: Update ListTables failed");
        }
        table.setEditable(true);
    }


    @FXML
    private void Test(MouseEvent event) {
        Main.db.fillTable(listtables.getFocusModel().getFocusedItem().toString(), table);
    }
}
