package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.controllers.MenuController;
import ru.bigtows.util.Columns;
import ru.bigtows.util.DataBase;
import ru.bigtows.util.Debug;

import java.sql.SQLException;

/**
 * Created by bigtows on 27/03/2017.
 */
public class Country {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;

    public Country(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public static void fillCountry(TableView table, HBox hb) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер", new PropertyValueFactory<Country, String>("id"));
        TableColumn name = Columns.getColumn("Название", new PropertyValueFactory<Country, String>("name"));
        /**
         * add Event edit
         */
        id.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Country, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Country, String> t) {
                String oldValue = t.getOldValue();
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setId(t.getNewValue());
                dbConnector.updateCountry(t.getRowValue(), oldValue);
            }
        });
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Country, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Country, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setName(t.getNewValue());
                dbConnector.updateCountry(t.getRowValue(), t.getRowValue().getId());
            }
        });
        /**
         * add Column to Table
         */
        table.getColumns().addAll(name);


        table.setItems(dbConnector.getCountryTable());

        Debug.log("[Country class]: Remove listener " + EditingTable.lastTable);

        if (EditingTable.getListener(EditingTable.lastTable) != null) {
            table.getSelectionModel().selectedItemProperty().removeListener(EditingTable.getListener(EditingTable.lastTable));
        }
        table.getSelectionModel().selectedItemProperty().addListener(EditingTable.getListener("country"));

        final TextField nameField = new TextField();
        nameField.setPromptText("Название");
        final Button addButton = new Button("Отправить");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dbConnector.addCountry(nameField.getText());
                MenuController.fillTable("country", table, hb);
            }
        });
        hb.getChildren().addAll(nameField, addButton);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
