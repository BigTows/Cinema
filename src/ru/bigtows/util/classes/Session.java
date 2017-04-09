package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.controllers.MenuController;
import ru.bigtows.util.Columns;
import ru.bigtows.util.DataBase;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by bigtows on 30/03/2017.
 */
public class Session {
    private final SimpleStringProperty id;
    private final SimpleStringProperty idR;
    private final SimpleStringProperty idF;
    private final SimpleStringProperty idT;
    private final SimpleStringProperty idC;
    private final SimpleStringProperty date;


    public Session(String id, String idR, String idF, String idC, String idT, String date) {
        this.id = new SimpleStringProperty(id);
        this.idR = new SimpleStringProperty(idR);
        this.idF = new SimpleStringProperty(idF);
        this.idT = new SimpleStringProperty(idT);
        this.date = new SimpleStringProperty(date);
        this.idC = new SimpleStringProperty(idC);
    }

    public static void fillSession(TableView table, HBox hb) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер сеанса", new PropertyValueFactory<Film, String>("id"));
        TableColumn idR = Columns.getColumn("Номер зала", new PropertyValueFactory<Film, String>("idR"));
        TableColumn idF = Columns.getColumn("Номер фильма", new PropertyValueFactory<Film, String>("idF"));
        TableColumn idT = Columns.getColumn("Номер типа", new PropertyValueFactory<Film, String>("idT"));
        TableColumn idC = Columns.getColumn("Номер кинотеатра", new PropertyValueFactory<Film, String>("idT"));
        TableColumn date = Columns.getColumn("Дата", new PropertyValueFactory<Film, String>("date"));
        id.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                String oldId = t.getOldValue();
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setId(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), oldId);
            }
        });
        idR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdR(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });

        idF.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdF(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });

        idT.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdT(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });

        idC.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdC(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setDate(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });

        table.getColumns().addAll(id, idC, idF, idR, idT, date);
        table.setItems(dbConnector.getSessionTable());

        final TextField idRField = new TextField();
        idRField.setPromptText("Номер комнаты");

        /**
         * Clean this
         */
        ComboBox filmComboBox = new ComboBox();
        filmComboBox.setPromptText("Фильм");
        HashMap<String, String> filmMap = new HashMap<>();
        Main.db.getFilmTable().forEach((tab) -> {
            filmMap.put(tab.getName(), tab.getId());
            filmComboBox.getItems().addAll(tab.getName());
        });

        ComboBox typeSessionComboBox = new ComboBox();
        typeSessionComboBox.setPromptText("Вид сеанса");
        HashMap<String, String> typeSessionMap = new HashMap<>();
        Main.db.getTypeSessionTable().forEach((tab) -> {
            typeSessionMap.put(tab.getName(), tab.getId());
            typeSessionComboBox.getItems().addAll(tab.getName());
        });


        ComboBox cinemaComboBox = new ComboBox();
        cinemaComboBox.setPromptText("Кинотеатры");
        HashMap<String, String> cinemaMap = new HashMap<>();
        Main.db.getCinemaTable().forEach((tab) -> {
            cinemaMap.put(tab.getName(), tab.getId());
            cinemaComboBox.getItems().addAll(tab.getName());
        });

        DatePicker datePicker = new DatePicker();

        ComboBox hourComboBox = new ComboBox();
        hourComboBox.setPromptText("Часы");
        ComboBox minuteComboBox = new ComboBox();
        minuteComboBox.setPromptText("Минуты");
        for (int i = 1; i <= 60; i++) {
            if (i <= 24) hourComboBox.getItems().addAll(i);
            minuteComboBox.getItems().addAll(i);
        }


        final Button addButton = new Button("Отправить");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dbConnector.addSession(idRField.getText(),
                        filmMap.get(filmComboBox.getSelectionModel().getSelectedItem().toString()),
                        typeSessionMap.get(typeSessionComboBox.getSelectionModel().getSelectedItem().toString()),
                        cinemaMap.get(cinemaComboBox.getSelectionModel().getSelectedItem().toString()),
                        ((datePicker.getValue().toString() + " " + hourComboBox.getSelectionModel().getSelectedItem().toString() + ":" + minuteComboBox.getSelectionModel().getSelectedItem().toString() + ":00")));
                MenuController.fillTable("session", table, hb);
            }
        });
        hb.getChildren().addAll(idRField, filmComboBox, typeSessionComboBox, cinemaComboBox, datePicker,
                hourComboBox, minuteComboBox, addButton);
    }

    public String getIdR() {
        return idR.get();
    }

    public String getIdF() {
        return idF.get();
    }


    public String getIdT() {
        return idT.get();
    }

    public String getIdC() {
        return idC.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setIdR(String id) {
        this.idR.set(id);
    }

    public void setIdC(String id) {
        this.idC.set(id);
    }

    public void setIdF(String id) {
        this.idF.set(id);
    }

    public void setIdT(String id) {
        this.idT.set(id);
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
