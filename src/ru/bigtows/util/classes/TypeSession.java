package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.controllers.MenuController;
import ru.bigtows.util.Columns;
import ru.bigtows.util.DataBase;
import ru.bigtows.util.Debug;

import java.sql.SQLException;

/**
 * Created by bigtows on 29/03/2017.
 */
public class TypeSession {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;


    public TypeSession(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }


    public static void fillTypeSession(TableView table, HBox hb) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер типа сессии", new PropertyValueFactory<Film, String>("id"));
        TableColumn name = Columns.getColumn("Название сессии", new PropertyValueFactory<Film, String>("name"));
        id.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TypeSession, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TypeSession, String> t) {
                String oldId = t.getOldValue();
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setId(t.getNewValue());
                dbConnector.updateTypeSession(t.getRowValue(), oldId);
            }
        });
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TypeSession, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TypeSession, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setName(t.getNewValue());
                dbConnector.updateTypeSession(t.getRowValue(), t.getRowValue().getId());
            }
        });
        table.getColumns().addAll(name);


        table.setItems(dbConnector.getTypeSessionTable());
        final TextField nameField = new TextField();
        nameField.setPromptText("Название сессии");
        final Button addButton = new Button("Отправить");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dbConnector.addTypeSession(nameField.getText());
                MenuController.fillTable("type_session", table, hb);
            }
        });
        hb.getChildren().addAll(nameField, addButton);
        Debug.log("[Type Session class]: Remove listener " + EditingTable.lastTable);
        if (EditingTable.getListener(EditingTable.lastTable) != null) {
            table.getSelectionModel().selectedItemProperty().removeListener(EditingTable.getListener(EditingTable.lastTable));
        }
        table.getSelectionModel().selectedItemProperty().addListener(EditingTable.getListener("type_session"));
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }


    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
