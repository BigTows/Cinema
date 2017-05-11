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
                //@TODO Costil'
                if (!rs.getString(1).equalsIgnoreCase("logs"))
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

    public ObservableList<Log> getLogs() throws SQLException {
        ObservableList<Log> data = FXCollections.observableArrayList();
        if (this.status) {
            ResultSet dataTable = this.connect.createStatement().executeQuery("SELECT * FROM Logs");

            while (dataTable.next()) {
                Log log = new Log(dataTable.getString(1),
                        dataTable.getString(2),
                        dataTable.getString(3),
                        dataTable.getString(4)
                );
                data.add(log);
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


    public void updateCountry(Country country, String oldID) {
        try {
            String querySQL = "call updateCountry(?,?,?)";
            PreparedStatement updCountry = this.connect.prepareStatement(querySQL);
            updCountry.setString(1, country.getId());
            updCountry.setString(2, country.getName());
            updCountry.setString(3, oldID);
            updCountry.executeUpdate();
            Debug.log("[DataBase]: Update country" + country.getName());
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
            Debug.log("[DataBase]: Update session " + session.getId());

        } catch (SQLException e) {
            alertError(e);
        }
    }


    public void updateFilm(Film rowValue, String id) {
        try {
            PreparedStatement film = this.connect.prepareStatement("call updateFilm(?,?,?,?,?)");
            film.setString(1, rowValue.getId());
            film.setString(2, rowValue.getIdC());
            film.setString(3, rowValue.getName());
            film.setString(4, rowValue.getDuration());
            film.setString(5, id);
            film.executeUpdate();
            Debug.log("[DataBase]: Update film " + rowValue.getName());
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void updateCinema(Cinema rowValue, String id) {
        try {
            String sqlQuery = "call updateCinema(" + rowValue.getId() + ",'" + rowValue.getName() +
                    "','" + rowValue.getAddress() + "'," + id + ")";
            PreparedStatement cinema = this.connect.prepareStatement("call updateCinema(?,?,?,?)");
            cinema.setString(1, rowValue.getId());
            cinema.setString(2, rowValue.getName());
            cinema.setString(3, rowValue.getAddress());
            cinema.setString(4, id);
            cinema.executeUpdate();
            Debug.log("[DataBase]: Update cinema " + rowValue.getName());
            this.connect.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void updateTypeSession(TypeSession rowValue, String id) {
        try {
            PreparedStatement typeSession = this.connect.prepareStatement("call updateTypeSession(?,?,?)");
            typeSession.setString(1, rowValue.getId());
            typeSession.setString(2, rowValue.getName());
            typeSession.setString(3, id);
            typeSession.executeQuery();
            Debug.log("[DataBase]: Update type session " + id);
        } catch (SQLException e) {
            alertError(e);
        }
    }


    public void addCinema(String name, String address) {
        try {
            PreparedStatement cinema = this.connect.prepareStatement("call addCinema(?,?)");
            cinema.setString(1, name);
            cinema.setString(2, address);
            cinema.executeQuery();
            Debug.log("[DataBase]: Add cinema " + name);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void addCountry(String name) {
        try {
            String querySQL = "call addCountry(?)";
            PreparedStatement addCountry = this.connect.prepareStatement(querySQL);
            addCountry.setString(1, name);
            addCountry.executeQuery();
            Debug.log("[DataBase]: Add new country " + name);
        } catch (SQLException e) {
            alertError(e);
        }


    }

    public void addFilm(String name, String duration, String country) {
        try {
            PreparedStatement film = this.connect.prepareStatement("call addFilm(?,?,?)");
            film.setString(1, name);
            film.setString(2, duration);
            film.setString(3, country);
            film.executeQuery();
            Debug.log("[DataBase]: Add film " + name);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void addTypeSession(String name) {
        try {
            PreparedStatement typeSession = this.connect.prepareStatement("call addTypeSession(?)");
            typeSession.setString(1, name);
            typeSession.executeQuery();
            Debug.log("[DataBase]: Add type session " + name);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void addSession(String idR, String idF, String idT, String idC, String date) {
        try {
            PreparedStatement session = this.connect.prepareStatement("call addSession(?,?,?,?,?)");
            session.setString(1, idT);
            session.setString(2, idC);
            session.setString(3, idF);
            session.setString(4, date);
            session.setString(5, idR);
            session.executeQuery();
            Debug.log("[DataBase]: Add new session");
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeFilm(String id) {
        try {
            PreparedStatement film = this.connect.prepareStatement("call removeFilm(?)");
            film.setString(1, id);
            film.executeQuery();
            Debug.log("[DataBase]: Remove film " + id);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeCinema(String id) {
        try {
            PreparedStatement cinema = this.connect.prepareStatement("call removeCinema(?)");
            cinema.setString(1, id);
            cinema.executeQuery();
            Debug.log("[DataBase]: Remove cinema " + id);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeSession(String id) {
        try {
            PreparedStatement session = this.connect.prepareStatement("call removeSession(?)");
            session.setString(1, id);
            session.executeQuery();
            Debug.log("[DataBase]: Remove session " + id);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeTypeSession(String id) {
        try {
            PreparedStatement typeSession = this.connect.prepareStatement("call removeTypeSession(?)");
            typeSession.setString(1, id);
            typeSession.executeQuery();
            Debug.log("[DataBase]: Remove type session " + id);
        } catch (SQLException e) {
            alertError(e);
        }
    }

    public void removeCountry(String id) {
        try {
            PreparedStatement country = this.connect.prepareStatement("call removeCountry(?)");
            country.setString(1, id);
            country.executeQuery();
            Debug.log("[DataBase]: Remove country " + id);
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

    private void alertInfo(String shortMessage, String fullMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(shortMessage);
        alert.setContentText(fullMessage);
        alert.show();
    }
}
