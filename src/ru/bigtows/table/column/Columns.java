package ru.bigtows.table.column;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ru.bigtows.table.action.EditingTable;

/**
 * Created by bigtows on 29/03/2017.
 *
 * @TODO CHANGE
 */
public class Columns {


    public static TableColumn getColumn(String name, PropertyValueFactory<?, String> pvf, EventHandler<CellEditEvent<?, String>> eventHandler) {

        TableColumn column = new TableColumn(name);

        column.setMinWidth(100);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingTable();
                    }
                };
        column.setCellValueFactory(pvf);
        column.setCellFactory(cellFactory);
        column.setOnEditCommit(eventHandler);
        return column;


    }

    public static TableColumn getColumn(String name, PropertyValueFactory<?, String> pvf) {

        TableColumn column = new TableColumn(name);

        column.setMinWidth(100);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingTable();
                    }
                };
        column.setCellValueFactory(pvf);
        column.setCellFactory(cellFactory);
        return column;


    }
}
