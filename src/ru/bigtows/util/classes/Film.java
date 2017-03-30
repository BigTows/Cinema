package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.bigtows.Main;
import ru.bigtows.util.Columns;
import ru.bigtows.util.DataBase;

import java.sql.SQLException;

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

    public static void fillFilm(TableView table) throws SQLException {
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
        table.setItems(dbConnector.getFilmTable());

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
