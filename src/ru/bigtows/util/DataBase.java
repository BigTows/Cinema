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

    public HashMap<Integer, ArrayList<String>> getTable(String name) throws SQLException {
        HashMap<Integer, ArrayList<String>> items = new HashMap<Integer, ArrayList<String>>();

        if (this.status) {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT * FROM " + name);
            ResultSetMetaData rsmd = rs.getMetaData();
            ArrayList<String> columnname = new ArrayList<String>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columnname.add(rsmd.getColumnName(i));
            }
            items.put(0, columnname);
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<String>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                items.put(rs.getRow(), row);
            }
        } else {
            Debug.log("[DataBase]: Please reconnect");
        }
        return items;
    }
}
