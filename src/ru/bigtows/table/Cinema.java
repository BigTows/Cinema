package ru.bigtows.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.controllers.MenuController;
import ru.bigtows.table.action.EditingTable;
import ru.bigtows.table.column.Columns;
import ru.bigtows.util.DataBase;
import ru.bigtows.util.Debug;

import java.sql.SQLException;

/**
 * Cinema
 * Created by bigtows
 * GitHub - https://github.com/BigTows
 * from 29/03/2017
 */
public class Cinema {
    private final SimpleStringProperty idCinema;
    private final SimpleStringProperty nameCinema;
    private final SimpleStringProperty addressCinema;


    public Cinema(String idCinema, String nameCinema, String addressCinema) {
        this.idCinema = new SimpleStringProperty(idCinema);
        this.nameCinema = new SimpleStringProperty(nameCinema);
        this.addressCinema = new SimpleStringProperty(addressCinema);
    }

    public static void fillCinema(TableView table, HBox hb) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер кинотеатра", new PropertyValueFactory<Cinema, String>("idCinema"));
        TableColumn name = Columns.getColumn("Название", new PropertyValueFactory<Cinema, String>("nameCinema"));
        TableColumn address = Columns.getColumn("Адрес", new PropertyValueFactory<Cinema, String>("addressCinema"));

        id.setOnEditCommit(new EventHandler<CellEditEvent<Cinema, String>>() {
            @Override
            public void handle(CellEditEvent<Cinema, String> t) {
                String oldId = t.getOldValue();
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdCinema(t.getNewValue());
                dbConnector.updateCinema(t.getRowValue(), oldId);
            }
        });
        name.setOnEditCommit(new EventHandler<CellEditEvent<Cinema, String>>() {
            @Override
            public void handle(CellEditEvent<Cinema, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setNameCinema(t.getNewValue());
                dbConnector.updateCinema(t.getRowValue(), t.getRowValue().getIdCinema());
            }
        });
        address.setOnEditCommit(new EventHandler<CellEditEvent<Cinema, String>>() {
            @Override
            public void handle(CellEditEvent<Cinema, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setAddressCinema(t.getNewValue());
                dbConnector.updateCinema(t.getRowValue(), t.getRowValue().getIdCinema());
            }
        });
        table.getColumns().addAll(name, address);
        table.setItems(dbConnector.getCinemaTable());
        Debug.log("[Cinema class]: Remove listener " + EditingTable.lastTable);
        if (EditingTable.getListener(EditingTable.lastTable) != null) {
            table.getSelectionModel().selectedItemProperty().removeListener(EditingTable.getListener(EditingTable.lastTable));
        }


        table.getSelectionModel().selectedItemProperty().addListener(EditingTable.getListener("cinema"));

        /**
         * Add Buttons for input
         */
        final TextField nameField = new TextField();
        nameField.setPromptText("Название");
        final TextField addressField = new TextField();
        addressField.setPromptText("Адрес");

        final Button addButton = new Button("Отправить");
        addButton.setOnAction(e -> {
            dbConnector.addCinema(nameField.getText(), addressField.getText());
            MenuController.fillTable("cinema", table, hb);
        });
        hb.getChildren().addAll(nameField, addressField, addButton);
    }


    public String getNameCinema() {
        return nameCinema.get();
    }

    public void setNameCinema(String nameCinema) {
        this.nameCinema.set(nameCinema);
    }

    public String getIdCinema() {
        return idCinema.get();
    }

    public void setIdCinema(String idCinema) {
        this.idCinema.set(idCinema);
    }

    public String getAddressCinema() {
        return addressCinema.get();
    }

    public void setAddressCinema(String addressCinema) {
        this.addressCinema.set(addressCinema);
    }
}
