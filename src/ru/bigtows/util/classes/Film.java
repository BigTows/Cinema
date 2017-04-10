package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.controllers.MenuController;
import ru.bigtows.util.Columns;
import ru.bigtows.util.DataBase;
import ru.bigtows.util.Debug;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by bigtows on 29/03/2017.
 */
public class Film {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty duration;
    private final SimpleStringProperty idC;


    public Film(String id, String name, String duration, String id_country) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.duration = new SimpleStringProperty(duration);
        this.idC = new SimpleStringProperty(id_country);
    }

    public static void fillFilm(TableView table, HBox hb) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер фильма", new PropertyValueFactory<Film, String>("id"));
        TableColumn name = Columns.getColumn("Название", new PropertyValueFactory<Film, String>("name"));
        TableColumn duration = Columns.getColumn("Длительность", new PropertyValueFactory<Film, String>("duration"));
        TableColumn idCountry = Columns.getColumn("Номер страны", new PropertyValueFactory<Film, String>("idC"));
        /**
         * add Event edit
         */
        id.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Film, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Film, String> t) {
                String oldId = t.getOldValue();
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setId(t.getNewValue());
                dbConnector.updateFilm(t.getRowValue(), oldId);
            }
        });
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Film, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Film, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setName(t.getNewValue());
                dbConnector.updateFilm(t.getRowValue(), t.getRowValue().getId());
            }
        });

        duration.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Film, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Film, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setDuration(t.getNewValue());
                dbConnector.updateFilm(t.getRowValue(), t.getRowValue().getId());
            }
        });

        idCountry.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Film, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Film, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setCountry(t.getNewValue());
                dbConnector.updateFilm(t.getRowValue(), t.getRowValue().getId());
            }
        });

        table.getColumns().addAll(id, name, duration, idCountry);
        Debug.log("[Film class]: Remove listener " + EditingTable.lastTable);
        if (EditingTable.getListener(EditingTable.lastTable) != null) {
            table.getSelectionModel().selectedItemProperty().removeListener(EditingTable.getListener(EditingTable.lastTable));
        }
        table.getSelectionModel().selectedItemProperty().addListener(EditingTable.getListener("film"));

        table.setItems(dbConnector.getFilmTable());
        /**
         * Add Buttons for input
         */
        final TextField nameField = new TextField();
        nameField.setPromptText("Название");
        final TextField durationField = new TextField();
        durationField.setPromptText("Длительность");
        final ComboBox countryComboBox = new ComboBox();
        HashMap<String, String> countrysMap = new HashMap<>();
        ObservableList<Country> countrysList = Main.db.getCountryTable();
        countrysList.forEach((tab) -> {
            countrysMap.put(tab.getName(), tab.getId());
            countryComboBox.getItems().addAll(tab.getName());
        });

        final Button addButton = new Button("Отправить");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dbConnector.addFilm(nameField.getText(), durationField.getText(), countrysMap.get(countryComboBox.getSelectionModel().getSelectedItem().toString()));
                MenuController.fillTable("film", table, hb);
            }
        });
        hb.getChildren().addAll(nameField, durationField, countryComboBox, addButton);

    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public String getDuration() {
        return duration.get();
    }

    public String getIdC() {
        return idC.get();
    }


    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public void setCountry(String country) {
        this.idC.set(country);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
