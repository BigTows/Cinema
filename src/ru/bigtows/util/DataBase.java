package ru.bigtows.util;

/**
 * Created by bigtows on 19/03/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import ru.bigtows.util.classes.*;

import java.sql.*;

public class DataBase {

    private Connection connect = null;
    private boolean status = false;


    public DataBase(String host, String name, String user, String password) {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" +
                    host + "/" + name +
                    "?user=" + user + "&password=" + password);
            Debug.log("[DataBase]: Connected success");
            this.status = true;
        } catch (SQLException ex) {
            Debug.log("[DataBase]: SQLException: " + ex.getMessage());
            Debug.log("[DataBase]: SQLState: " + ex.getSQLState());
            Debug.log("[DataBase]: VendorError: " + ex.getErrorCode());
        }
    }

    public void destroy() {
        this.connect = null;
        this.status = false;
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
        if (this.status) {
            ResultSet dataTable = this.connect.createStatement().executeQuery("call getCountry");

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
                        dataTable.getString(2),
                        dataTable.getString(3),
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
                alertError(e);
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
                alertError(e);
            }
        }
        return data;
    }


    public void addCountry(String name) {
        try {
            String querySQL = "call addCountry(?)";
            PreparedStatement addCountry = this.connect.prepareStatement(querySQL);
            addCountry.setString(1, name);
            addCountry.executeQuery();
            Debug.log("[DataBase]: add " + name + " Country");
        } catch (SQLException e) {
            alertError(e);
        }


    }

    public void updateCountry(Country country, String oldID) {
        try {
            String querySQL = "call updateCountry(?,?,?)";
            PreparedStatement updCountry = this.connect.prepareStatement(querySQL);
            updCountry.setString(1, country.getId());
            updCountry.setString(2, country.getName());
            updCountry.setString(3, oldID);
            updCountry.executeUpdate();
            Debug.log("[DataBase]: update " + country.getName() + " Country");
        } catch (SQLException e) {
            alertError(e);
        }
    }


    public void updateSession(Session session, String oldId) {
        try {
            String querySQL = "call updateSession(?,?,?,?,?,?,?)";
            PreparedStatement updCountry = this.connect.prepareStatement(querySQL);
            updCountry.setString(1, session.getIdC());
            updCountry.setString(2, session.getIdR());
            updCountry.setString(3, session.getIdT());
            updCountry.setString(4, session.getIdF());
            updCountry.setString(5, session.getDate());
            updCountry.setString(6, session.getId());
            updCountry.setString(7, oldId);
            updCountry.executeUpdate();
            Debug.log("[DataBase]: update " + session.getId() + " Session");

        } catch (SQLException e) {
            alertError(e);
        }
    }


    public void updateFilm(Film rowValue, String id) {
        try {
            String sqlQuery = "call updateFilm('" + rowValue.getId() + "'," + rowValue.getIdC()
                    + ",'" + rowValue.getName() + "'," + rowValue.getDuration() + "," + id + ")";
            Debug.log(sqlQuery);
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void updateCinema(Cinema rowValue, String id) {
        try {
            String sqlQuery = "call updateCinema(" + rowValue.getId() + ",'" + rowValue.getName() +
                    "','" + rowValue.getAddress() + "'," + id + ")";
            Debug.log(sqlQuery);
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            alertError(e);
        }
    }


    public void addCinema(String name, String address) {
        try {
            this.connect.createStatement().executeQuery("call addCinema('" + name + "','" + address + "')");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void addFilm(String name, String duration, String country) {
        try {
            this.connect.createStatement().executeQuery("call addFilm('" + name + "'," + duration +
                    "," + country + ")");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void updateTypeSession(TypeSession rowValue, String id) {
        try {
            String sqlQuery = "call updateTypeSession(" + rowValue.getId() + ",'" + rowValue.getName() +
                    "'," + id + ")";
            Debug.log(sqlQuery);
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void addTypeSession(String name) {
        try {
            this.connect.createStatement().executeQuery("call addTypeSession('" + name + "')");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void addSession(String idR, String idF, String idT, String idC, String date) {
        try {
            this.connect.createStatement().executeQuery("call addSession(" + idT + "," + idC + "," + idF + ",'" + date + "'," + idR + ")");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeFilm(String id) {
        try {
            this.connect.createStatement().executeQuery("call removeFilm(" + id + ")");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeCinema(String id) {
        try {
            this.connect.createStatement().executeQuery("call removeCinema(" + id + ")");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeSession(String id) {
        try {
            this.connect.createStatement().executeQuery("call removeSession(" + id + ")");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeTypeSession(String id) {
        try {
            this.connect.createStatement().executeQuery("call removeTypeSession(" + id + ")");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeCountry(String id) {
        try {
            this.connect.createStatement().executeQuery("call removeCountry(" + id + ")");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public ResultSet getUsers() {
        try {
            return this.connect.createStatement().executeQuery("SELECT DISTINCT User FROM mysql.user WHERE is_role='N' AND LENGTH(User)>0");
        } catch (SQLException e) {
            alertError(e);
            return null;
        }
    }

    public ResultSet getGrants(String userName) {
        try {
            String querySQL = "SELECT ROLE FROM mysql.roles_mapping WHERE USER LIKE ?";
            PreparedStatement user = this.connect.prepareStatement(querySQL);
            user.setString(1, userName + "%");
            return user.executeQuery();
        } catch (SQLException e) {
            alertError(e);
            return null;
        }
    }

    public boolean isAdmin() {
        try {
            this.connect.createStatement().executeQuery("SELECT User FROM mysql.user WHERE is_role='Y'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ResultSet getRoles() {
        try {
            return this.connect.createStatement().executeQuery("SELECT User FROM mysql.user WHERE is_role='Y'");
        } catch (SQLException e) {
            alertError(e);
            return null;
        }
    }

    public boolean removeUser(String userName) {
        try {
            String querySQL = "DROP USER IF EXISTS ?@localhost";
            PreparedStatement user = this.connect.prepareStatement(querySQL);
            user.setString(1, userName);
            user.executeQuery();
            return true;
        } catch (SQLException e) {
            alertError(e);
            return false;
        }
    }

    public boolean addUser(String userName, String password, String role) {
        try {
            String querySQL = "CREATE USER ?@localhost IDENTIFIED BY ?";
            PreparedStatement user = this.connect.prepareStatement(querySQL);
            user.setString(1, userName);
            user.setString(2, password);
            user.executeQuery();

            querySQL = "GRANT ? TO ?@localhost";
            user = this.connect.prepareStatement(querySQL);
            user.setString(1, role);
            user.setString(2, userName);
            user.executeQuery();

            querySQL = "SET DEFAULT ROLE ? FOR ?@localhost";
            user = this.connect.prepareStatement(querySQL);
            user.setString(1, role);
            user.setString(2, userName);
            user.executeQuery();

            Debug.log("[AdminPanel]: Create user " + userName + " and add role " + role);
            return true;
        } catch (SQLException e) {
            alertError(e);
            return false;
        }
    }

    private void alertError(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error code: " + e.getErrorCode());
        alert.setContentText(e.getMessage());
        alert.show();
    }
}
