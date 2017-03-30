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

import java.sql.SQLException;

/**
 * Created by bigtows on 29/03/2017.
 */
public class Cinema {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;


    public Cinema(String id, String name, String address) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
    }

    public static void fillCinema(TableView table, HBox hb) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер кинотеатра", new PropertyValueFactory<Cinema, String>("id"));
        TableColumn name = Columns.getColumn("Название", new PropertyValueFactory<Cinema, String>("name"));
        TableColumn address = Columns.getColumn("Адрес", new PropertyValueFactory<Cinema, String>("address"));

        id.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cinema, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cinema, String> t) {
                String oldId = t.getOldValue();
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setId(t.getNewValue());
                dbConnector.updateCinema(t.getRowValue(), oldId);
            }
        });
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cinema, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cinema, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setName(t.getNewValue());
                dbConnector.updateCinema(t.getRowValue(), t.getRowValue().getId());
            }
        });
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cinema, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cinema, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setAddress(t.getNewValue());
                dbConnector.updateCinema(t.getRowValue(), t.getRowValue().getId());
            }
        });
        table.getColumns().addAll(id, name, address);
        table.setItems(dbConnector.getCinemaTable());

        /**
         * Add Buttons for input
         */
        final TextField nameField = new TextField();
        nameField.setPromptText("Название");
        final TextField addressField = new TextField();
        addressField.setPromptText("Адрес");
        final Button addButton = new Button("Отправить");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dbConnector.addCinema(nameField.getText(), addressField.getText());
                MenuController.fillTable("cinema", table, hb);
            }
        });
        hb.getChildren().addAll(nameField, addressField, addButton);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
