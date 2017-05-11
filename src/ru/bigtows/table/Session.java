package ru.bigtows.table;

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
 * Created by bigtows on 30/03/2017.
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


    public Session(String id, String idR, String idF, String idC, String idT, String date, String nameFilm, String nameCinema) {
        this.id = new SimpleStringProperty(id);
        this.idR = new SimpleStringProperty(idR);
        this.idF = new SimpleStringProperty(idF);
        this.nameFilm = new SimpleStringProperty(nameFilm);
        this.idT = new SimpleStringProperty(idT);
        this.date = new SimpleStringProperty(date);
        this.idC = new SimpleStringProperty(idC);
        this.nameCinema = new SimpleStringProperty(nameCinema);
    }

    public static void fillSession(TableView table, HBox hb) throws SQLException {
        DataBase dbConnector = Main.db;
        TableColumn id = Columns.getColumn("Номер сеанса", new PropertyValueFactory<Session, String>("id"));
        TableColumn idR = Columns.getColumn("Номер зала", new PropertyValueFactory<Session, String>("idR"));
        TableColumn nameF = Columns.getColumn("Название фильма", new PropertyValueFactory<Session, String>("nameFilm"));
        TableColumn idT = Columns.getColumn("Номер типа", new PropertyValueFactory<Session, String>("idT"));
        TableColumn nameCinema = Columns.getColumn("Номер кинотеатра", new PropertyValueFactory<Session, String>("nameCinema"));
        TableColumn date = Columns.getColumn("Дата", new PropertyValueFactory<Session, String>("date"));
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

        nameF.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Session, String>>() {
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
        table.getColumns().addAll(id, nameCinema, nameF, idR, idT, date);
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
            cinemaMap.put(tab.getNameCinema(), tab.getIdCinema());
            cinemaComboBox.getItems().addAll(tab.getNameCinema());
        });

        DatePicker datePicker = new DatePicker();

        ComboBox hourComboBox = new ComboBox();
        hourComboBox.setPromptText("Часы");
        ComboBox minuteComboBox = new ComboBox();
        minuteComboBox.setPromptText("Минуты");
        for (int i = 1; i <= 59; i++) {
            if (i <= 23) hourComboBox.getItems().addAll(i);
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

        Debug.log("[Session class]: Remove listener " + EditingTable.lastTable);
        if (EditingTable.getListener(EditingTable.lastTable) != null) {
            table.getSelectionModel().selectedItemProperty().removeListener(EditingTable.getListener(EditingTable.lastTable));
        }


        table.getSelectionModel().selectedItemProperty().addListener(EditingTable.getListener("session"));
    }

    public String getIdR() {
        return idR.get();
    }

    public String getIdF() {
        return idF.get();
    }

    public String getNameCinema() {
        return nameCinema.get();
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

    public String getNameFilm() {
        return this.nameFilm.get();
    }

    public void setNameFilm(String name) {
        this.nameFilm.set(name);
    }

    public void setNameCinema(String name) {
        this.nameCinema.set(name);
    }
    public void setId(String id) {
        this.id.set(id);
    }

    private void setIdR(String id) {
        this.idR.set(id);
    }

    private void setIdC(String id) {
        this.idC.set(id);
    }

    private void setIdF(String id) {
        this.idF.set(id);
    }

    private void setIdT(String id) {
        this.idT.set(id);
    }

    private void setDate(String date) {
        this.date.set(date);
    }
}
