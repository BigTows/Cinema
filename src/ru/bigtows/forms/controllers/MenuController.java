package ru.bigtows.forms.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import ru.bigtows.Main;
import ru.bigtows.util.Debug;

import ru.bigtows.util.GeneralObservabel;
import ru.bigtows.util.Observer;

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
                            toTable(Main.db.getTable(new_val));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public void toTable(HashMap<Integer,ArrayList<String>> items){
        table.getColumns().clear();
        ArrayList<TableColumn> colums =new ArrayList<TableColumn>();
        for (int i=0;i<items.get(0).size();i++)
            table.getColumns().addAll(new TableColumn(items.get(0).get(i)));
        for(int i=1;i<items.size()-1;i++){
            for (int j=0;j<items.get(0).size();j++){
                //table.setItems();
            }
        }
    }

    @FXML
    private void Test(MouseEvent event) {
        Debug.log(listtables.getFocusModel().getFocusedItem().toString());
    }
}
