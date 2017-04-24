package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.bigtows.Main;
import ru.bigtows.util.Columns;
import ru.bigtows.util.DataBase;

import java.sql.SQLException;

/**
 * Cinema
 * Created by bigtows.
 */
public class Log {
    private final SimpleStringProperty id;
    private final SimpleStringProperty date;
    private final SimpleStringProperty user;
    private final SimpleStringProperty message;


    public Log(String id, String date, String user, String message) {
        this.id = new SimpleStringProperty(id);
        this.date = new SimpleStringProperty(date);
        this.user = new SimpleStringProperty(user);
        this.message = new SimpleStringProperty(message);
    }

    public static void fillLog(TableView table) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер операции", new PropertyValueFactory<Log, String>("id"));
        TableColumn date = Columns.getColumn("Дата", new PropertyValueFactory<Log, String>("date"));
        TableColumn user = Columns.getColumn("Пользователь", new PropertyValueFactory<Log, String>("user"));
        TableColumn message = Columns.getColumn("Сообщение", new PropertyValueFactory<Log, String>("message"));
        table.getColumns().addAll(id, date, user, message);
        table.setItems(dbConnector.getLogs());
    }

    public String getId() {
        return id.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getUser() {
        return user.get();
    }

    public String getMessage() {
        return message.get();
    }

}
