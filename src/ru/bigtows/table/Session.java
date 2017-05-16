package ru.bigtows.table;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.controllers.MenuController;
import ru.bigtows.table.action.EditingTable;
import ru.bigtows.table.column.Columns;
import ru.bigtows.util.DataBase;
import ru.bigtows.util.Debug;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Cinema
 * Created by bigtows
 * GitHub - https://github.com/BigTows
 * from 30/03/2017
 */
public class Session {
    private final SimpleStringProperty id;
    private final SimpleStringProperty idR;
    private final SimpleStringProperty idF;
    private final SimpleStringProperty idT;
    private final SimpleStringProperty idC;
    private final SimpleStringProperty date;
    private final SimpleStringProperty nameFilm;
    private final SimpleStringProperty nameCinema;
    private final SimpleStringProperty nameTypeSession;


    public Session(String id, String idR, String idF, String idC, String idT, String date, String nameFilm, String nameCinema, String nameTypeSession) {
        this.id = new SimpleStringProperty(id);
        this.idR = new SimpleStringProperty(idR);
        this.idF = new SimpleStringProperty(idF);
        this.nameFilm = new SimpleStringProperty(nameFilm);
        this.idT = new SimpleStringProperty(idT);
        this.date = new SimpleStringProperty(date);
        this.idC = new SimpleStringProperty(idC);
        this.nameCinema = new SimpleStringProperty(nameCinema);
        this.nameTypeSession = new SimpleStringProperty(nameTypeSession);
    }

    public static void fillSession(TableView table, HBox hb, boolean... addInput) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn idSession = Columns.getColumn("Номер сеанса", new PropertyValueFactory<Session, String>("id"));
        TableColumn idRoom = Columns.getColumn("Номер зала", new PropertyValueFactory<Session, String>("idR"));
        TableColumn nameFilm = Columns.getColumn("Название фильма", new PropertyValueFactory<Session, String>("nameFilm"));
        TableColumn nameTypeSession = Columns.getColumn("Название типа", new PropertyValueFactory<Session, String>("nameTypeSession"));
        TableColumn nameCinema = Columns.getColumn("Название кинотеатра", new PropertyValueFactory<Session, String>("nameCinema"));
        TableColumn date = Columns.getColumn("Дата", new PropertyValueFactory<Session, String>("date"));
        idSession.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                String oldId = t.getOldValue();
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setId(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), oldId);
            }
        });
        idRoom.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdR(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });

        nameFilm.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdF(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });

        nameTypeSession.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Session, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setIdT(t.getNewValue());
                dbConnector.updateSession(t.getRowValue(), t.getRowValue().getId());
            }
        });

        nameCinema.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
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

        date.setMinWidth(170);
        table.getColumns().addAll(idSession, nameCinema, nameFilm, idRoom, nameTypeSession, date);
        table.setItems(dbConnector.getSessionTable());
        ReadOnlyObjectProperty tableObject = table.getSelectionModel().selectedItemProperty();
        Debug.log("[Session class]: Remove listener " + EditingTable.lastTable);
        if (EditingTable.getListener(EditingTable.lastTable) != null) {
            tableObject.removeListener(EditingTable.getListener(EditingTable.lastTable));
        }
        tableObject.addListener(EditingTable.getListener("session"));

        if (addInput.length == 0 || !addInput[0]) addInput(table, hb);
    }

    private static void addInput(TableView tableView, HBox hBox) throws SQLException {
        DataBase dbConnector = Main.db;
        final TextField idRField = new TextField();
        idRField.setPromptText("Номер комнаты");

        /**
         * Clean this
         */
        ComboBox<String> filmComboBox = new ComboBox<String>();
        filmComboBox.setPromptText("Фильм");
        HashMap<String, String> filmMap = new HashMap<>();
        Main.db.getFilmTable().forEach((tab) -> {
            filmMap.put(tab.getName(), tab.getId());
            filmComboBox.getItems().addAll(tab.getName());
        });

        ComboBox<String> typeSessionComboBox = new ComboBox<>();
        typeSessionComboBox.setPromptText("Вид сеанса");
        HashMap<String, String> typeSessionMap = new HashMap<>();
        Main.db.getTypeSessionTable().forEach((tab) -> {
            typeSessionMap.put(tab.getName(), tab.getId());
            typeSessionComboBox.getItems().addAll(tab.getName());
        });


        ComboBox<String> cinemaComboBox = new ComboBox<>();
        cinemaComboBox.setPromptText("Кинотеатры");
        HashMap<String, String> cinemaMap = new HashMap<>();
        Main.db.getCinemaTable().forEach((tab) -> {
            cinemaMap.put(tab.getNameCinema(), tab.getIdCinema());
            cinemaComboBox.getItems().addAll(tab.getNameCinema());
        });

        DatePicker datePicker = new DatePicker();

        ComboBox<String> hourComboBox = new ComboBox<>();
        hourComboBox.setPromptText("Часы");
        ComboBox<String> minuteComboBox = new ComboBox<>();
        minuteComboBox.setPromptText("Минуты");
        for (int i = 0; i <= 59; i++) {
            String element = i + "";
            if (i < 10) element = "0" + element;
            if (i <= 23) hourComboBox.getItems().addAll(element);
            minuteComboBox.getItems().addAll(element);
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
                MenuController.fillTable("session", tableView, hBox);
            }
        });
        hBox.getChildren().addAll(idRField, filmComboBox, typeSessionComboBox, cinemaComboBox, datePicker,
                hourComboBox, minuteComboBox, addButton);
    }


    public static void refresh(TableView table) throws SQLException {
        fillSession(table, null, true);
    }


    public String getIdR() {
        return idR.get();
    }

    private void setIdR(String id) {
        this.idR.set(id);
    }

    public String getIdF() {
        return idF.get();
    }

    private void setIdF(String id) {
        this.idF.set(id);
    }

    public String getNameCinema() {
        return nameCinema.get();
    }

    public void setNameCinema(String name) {
        this.nameCinema.set(name);
    }

    public String getIdT() {
        return idT.get();
    }

    private void setIdT(String id) {
        this.idT.set(id);
    }

    public String getIdC() {
        return idC.get();
    }

    private void setIdC(String id) {
        this.idC.set(id);
    }

    public String getDate() {
        return date.get();
    }

    private void setDate(String date) {
        this.date.set(date);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getNameTypeSession() {
        return nameTypeSession.get();
    }

    public void setNameTypeSession(String name) {
        this.nameTypeSession.set(name);
    }

    public String getNameFilm() {
        return this.nameFilm.get();
    }

    public void setNameFilm(String name) {
        this.nameFilm.set(name);
    }
}
