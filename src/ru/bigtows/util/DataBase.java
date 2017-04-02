package ru.bigtows.util;

/**
 * Created by bigtows on 19/03/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import ru.bigtows.util.classes.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public ObservableList<Session> getSessionTable() throws SQLException {
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


    public void addCountry(String name) {
        try {
            this.connect.createStatement().executeQuery("call addCountry('" + name + "')");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error code: " + e.getErrorCode());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void updateCountry(Country country, String oldID) {
        try {
            this.connect.createStatement().executeQuery("call updateCountry(" + country.getId() +
                    ",'" + country.getName() + "'," + oldID + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateSession(Session rowValue, String oldId) {
        try {
            String sqlQuery = "call updateSession(" + rowValue.getId() + "," + rowValue.getIdR()
                    + "," + rowValue.getIdT() + "," + rowValue.getIdF() + ",'" + rowValue.getDate() + "'," + rowValue.getIdC() + "," + oldId + ")";
            Debug.log(sqlQuery);
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void updateFilm(Film rowValue, String id) {
        try {
            String sqlQuery = "call updateFilm(" + rowValue.getId() + "," + rowValue.getIdC()
                    + ",'" + rowValue.getName() + "'," + rowValue.getDuration() + "," + id + ")";
            Debug.log(sqlQuery);
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCinema(Cinema rowValue, String id) {
        try {
            String sqlQuery = "call updateCinema(" + rowValue.getId() + ",'" + rowValue.getName() +
                    "','" + rowValue.getAddress() + "'," + id + ")";
            Debug.log(sqlQuery);
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addCinema(String name, String address) {
        try {
            this.connect.createStatement().executeQuery("call addCinema('" + name + "','" + address + "')");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error code: " + e.getErrorCode());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void addFilm(String name, String duration, String country) {
        try {
            this.connect.createStatement().executeQuery("call addFilm('" + name + "'," + duration +
                    "," + country + ")");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error code: " + e.getErrorCode());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void updateTypeSession(TypeSession rowValue, String id) {
        try {
            String sqlQuery = "call updateTypeSession(" + rowValue.getId() + ",'" + rowValue.getName() +
                    "'," + id + ")";
            Debug.log(sqlQuery);
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTypeSession(String name) {
        try {
            this.connect.createStatement().executeQuery("call addTypeSession('" + name + "')");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error code: " + e.getErrorCode());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void addSession(String idR, String idF, String idT, String idC, LocalDate date) {
        try {
            this.connect.createStatement().executeQuery("call addSession(" + idT + "," + idC + "," + idF + ",'" + date + "'," + idR + ")");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error code: " + e.getErrorCode());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
