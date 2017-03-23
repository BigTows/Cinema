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

    private ObservableList<ObservableList> data;

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
                            data = FXCollections.observableArrayList();
                            ResultSet rs = (Main.db.getTable(new_val));
                            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                                //We are using non property style for making dynamic table
                                final int j = i;
                                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                        return new SimpleStringProperty(param.getValue().get(j).toString());
                                    }
                                });

                                table.getColumns().addAll(col);
                                System.out.println("Column [" + i + "] ");
                            }

                            while (rs.next()) {
                                //Iterate Row
                                ObservableList<String> row = FXCollections.observableArrayList();
                                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                    //Iterate Column
                                    row.add(rs.getString(i));
                                }
                                System.out.println("Row [1] added " + row);
                                data.add(row);

                            }

                            //FINALLY ADDED TO TableView
                            table.setItems(data);
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
