package ru.bigtows.util;

/**
 * Created by bigtows on 19/03/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {

    private Connection connect = null;
    private String dbname = null;
    private boolean status = false;


    public DataBase(String host, String name, String user, String password) {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" +
                    host + "/" + name +
                    "?user=" + user + "&password=" + password);
            System.out.println("");
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

    public ResultSet getTable(String name) throws SQLException {

        if (this.status) {
            return this.connect.createStatement().executeQuery("SELECT * FROM " + name);


        } else {
            Debug.log("[DataBase]: Please reconnect");
        }
        return null;
    }
}
