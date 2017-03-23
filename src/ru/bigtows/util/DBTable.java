package ru.bigtows.util;

import javafx.scene.control.TableColumn;

import java.util.ArrayList;

/**
 * Created by bigtows on 23/03/2017.
 */
public class DBTable {

    private ArrayList<String> rows;

    public DBTable(ArrayList<String> row) {
        this.rows = row;
    }

    public String getItem(int item) {
        return rows.get(item);
    }
}
