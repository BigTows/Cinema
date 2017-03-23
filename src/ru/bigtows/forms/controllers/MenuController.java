package ru.bigtows.forms.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ru.bigtows.Main;
import ru.bigtows.util.Debug;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import ru.bigtows.util.GeneralObservabel;
import ru.bigtows.util.Observer;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
            Debug.log("[Form Menu]: Update ListTables Error");
        }

        listtables.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                                        String old_val, String new_val) {
                        try {
                            table.getColumns().clear();
                            Main.db.fillTableView(Main.db.getTable(new_val), table);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    @FXML
    private void Test(MouseEvent event) {
        Debug.log(listtables.getFocusModel().getFocusedItem().toString());
    }
}
