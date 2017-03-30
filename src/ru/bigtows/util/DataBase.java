package ru.bigtows.util;

/**
 * Created by bigtows on 19/03/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ru.bigtows.util.classes.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {

    private Connection connect = null;
    private String dbname = null;
    private boolean status = false;


    public DataBase(String host, String name, String user, String password) {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" +
                    host + "/" + name +
                    "?user=" + user + "&password=" + password);
            Debug.log("[DataBase]: Connected success");
            this.status = true;
            this.dbname = name;
        } catch (SQLException ex) {
            Debug.log("[DataBase]: SQLException: " + ex.getMessage());
            Debug.log("[DataBase]: SQLState: " + ex.getSQLState());
            Debug.log("[DataBase]: VendorError: " + ex.getErrorCode());
        }
    }

    public boolean getStatus() {
        return this.status;
    }

    public ObservableList<String> getTables() throws SQLException {
        ObservableList<String> items = FXCollections.observableArrayList();
        if (this.status) {
            ResultSet rs = this.connect.createStatement().executeQuery("SHOW TABLES");
            while (rs.next()) {
                items.add(rs.getString(1));
            }
        } else {
            Debug.log("[DataBase]: Please reconnect");
        }
        return items;
    }


    public ObservableList<Country> getCountryTable() throws SQLException {
        ObservableList<Country> data = FXCollections.observableArrayList();
        ResultSet dataTable = null;
        if (this.status) {
            dataTable = this.connect.createStatement().executeQuery("call getCountry");

            while (dataTable.next()) {
                Country country = new Country(dataTable.getString(2),
                        dataTable.getString(1));
                data.add(country);
            }
        }
        return data;
    }

    public ObservableList<Film> getFilmTable() throws SQLException {
        ObservableList<Film> data = FXCollections.observableArrayList();
        if (this.status) {
            ResultSet dataTable;
            dataTable = this.connect.createStatement().executeQuery("call getFilm");
            while (dataTable.next()) {
                Film film = new Film(dataTable.getString(1),
                        dataTable.getString(2), dataTable.getString(3), dataTable.getString(4));
                data.add(film);
            }
        }
        return data;
    }

    private ObservableList<Session> getSessionTable() throws SQLException {
        ObservableList<Session> data = FXCollections.observableArrayList();
        if (this.status) {
            ResultSet dataTable;
            dataTable = this.connect.createStatement().executeQuery("call getSession");
            while (dataTable.next()) {
                Session session = new Session(dataTable.getString(1),
                        dataTable.getString(2), dataTable.getString(3),
                        dataTable.getString(4),
                        dataTable.getString(5),
                        dataTable.getString(6));
                data.add(session);
            }
        }
        return data;
    }

    public ObservableList<Cinema> getCinemaTable() {
        ObservableList<Cinema> data = FXCollections.observableArrayList();
        if (this.status) {
            ResultSet dataTable;
            try {
                dataTable = this.connect.createStatement().executeQuery("call getCinema");
                while (dataTable.next()) {
                    Cinema cinema = new Cinema(dataTable.getString(1),
                            dataTable.getString(2), dataTable.getString(3));
                    data.add(cinema);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public ObservableList<TypeSession> getTypeSessionTable() {
        ObservableList<TypeSession> data = FXCollections.observableArrayList();
        if (this.status) {
            ResultSet dataTable;
            try {
                dataTable = this.connect.createStatement().executeQuery("call getTypeSession");
                while (dataTable.next()) {
                    TypeSession typeSession = new TypeSession(dataTable.getString(1),
                            dataTable.getString(2));
                    data.add(typeSession);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private void fillFilm(TableView table) throws SQLException {
        table.getColumns().addAll(
                Columns.getColumn("Номер фильма", new PropertyValueFactory<Film, String>("id"),
                        t -> ((Film) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCountry(t.getNewValue())),
                Columns.getColumn("Название", new PropertyValueFactory<Film, String>("name"),
                        t -> ((Film) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue())),
                Columns.getColumn("Длительность", new PropertyValueFactory<Film, String>("duration"),
                        t -> ((Film) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDuration(t.getNewValue())),
                Columns.getColumn("Номер страны", new PropertyValueFactory<Film, String>("idC"),
                        t -> ((Film) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCountry(t.getNewValue())));

        table.setItems(getFilmTable());

    }

    private void addCountry(String name) {
        try {
            this.connect.createStatement().executeQuery("call addCountry('" + name + "')");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error code: " + e.getErrorCode());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void fillCountry(TableView table, HBox hb) throws SQLException {
        table.getColumns().addAll(
                Columns.getColumn("Название", new PropertyValueFactory<Country, String>("name"),
                        t -> ((Country) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue())),
                Columns.getColumn("Номер", new PropertyValueFactory<Country, String>("id"),
                        t -> ((Country)
                                t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                        ).setId(t.getNewValue())));


        table.setItems(getCountryTable());
        final TextField addLastName = new TextField();
        addLastName.setPromptText("Название");
        final Button addButton = new Button("Отправить");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addCountry(addLastName.getText());
                fillTable("country", table, hb);
            }
        });
        hb.getChildren().addAll(addLastName, addButton);
    }

    private void fillTypeSession(TableView table) throws SQLException {
        table.getColumns().addAll(
                Columns.getColumn("Номер типа", new PropertyValueFactory<TypeSession, String>("id"),
                        t -> ((TypeSession) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setId(t.getNewValue())),
                Columns.getColumn("Номер кинотеатра", new PropertyValueFactory<TypeSession, String>("name"),
                        t -> ((TypeSession) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue())));

        table.setItems(getTypeSessionTable());
    }

    private void fillCinema(TableView table) throws SQLException {
        table.getColumns().addAll(
                Columns.getColumn("Номер кинотеатра", new PropertyValueFactory<Cinema, String>("id"),
                        t -> ((Cinema) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setId(t.getNewValue())),
                Columns.getColumn("Номер кинотеатра", new PropertyValueFactory<Cinema, String>("name"),
                        t -> ((Cinema) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue())),
                Columns.getColumn("Адрес",
                        new PropertyValueFactory<Cinema, String>("address"),
                        t -> ((Cinema) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAddress(t.getNewValue()))
        );
        table.setItems(getCinemaTable());

    }

    private void fillSession(TableView table) throws SQLException {
        table.getColumns().addAll(
                Columns.getColumn("Номер сеанса", new PropertyValueFactory<Session, String>("id"),
                        t -> ((Session) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setId(t.getNewValue())),
                Columns.getColumn("Номер зала", new PropertyValueFactory<Session, String>("idR"),
                        t -> ((Session) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setIdR(t.getNewValue())),
                Columns.getColumn("Номер фильма",
                        new PropertyValueFactory<Session, String>("idF"),
                        t -> ((Session) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setIdF(t.getNewValue())),
                Columns.getColumn("Номер типа",
                        new PropertyValueFactory<Session, String>("idT"),
                        t -> ((Session) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setIdT(t.getNewValue())),
                Columns.getColumn("Дата",
                        new PropertyValueFactory<Session, String>("date"),
                        t -> ((Session) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDate(t.getNewValue()))

        );
        table.setItems(getSessionTable());

    }


    public void fillTable(String nameTable, TableView table, HBox hb) {
        table.getColumns().clear();
        hb.getChildren().clear();
        switch (nameTable.toLowerCase()) {
            case "country": {
                try {
                    fillCountry(table, hb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "film": {
                try {
                    fillFilm(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "cinema": {
                try {
                    fillCinema(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "type_session": {
                try {
                    fillTypeSession(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "session": {
                try {
                    fillSession(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
