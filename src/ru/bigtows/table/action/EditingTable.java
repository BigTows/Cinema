package ru.bigtows.table.action;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import ru.bigtows.Main;
import ru.bigtows.table.*;
import ru.bigtows.util.Debug;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Cinema
 * Created by bigtows.
 */
public class EditingTable extends TableCell<Country, String> {

    public static String lastTable;
    private TextField textField;

    public static String selectedTable = null;
    public static String selectedIDTable = null;

    private static ChangeListener<Country> countryEdit = (observable, oldValue, newValue) -> {
        if (newValue == null) {
            EditingTable.selectedTable = null;
            return;
        }
        EditingTable.selectedTable = "country";
        EditingTable.selectedIDTable = newValue.getId();
    };
    private static ChangeListener<TypeSession> typeSessionEdit = (observable, oldValue, newValue) -> {
        if (newValue == null) {
            EditingTable.selectedTable = null;
            return;
        }
        EditingTable.selectedTable = "type_session";
        EditingTable.selectedIDTable = newValue.getId();
    };
    private static ChangeListener<Film> filmEdit = (observable, oldValue, newValue) -> {
        if (newValue == null) {
            EditingTable.selectedTable = null;
            return;
        }
        EditingTable.selectedTable = "film";
        EditingTable.selectedIDTable = newValue.getId();
    };
    private static ChangeListener<Session> sessionEdit = (observable, oldValue, newValue) -> {
        if (newValue == null) {
            EditingTable.selectedTable = null;
            return;
        }
        EditingTable.selectedTable = "session";
        EditingTable.selectedIDTable = newValue.getId();
    };
    private static ChangeListener<Cinema> cinemaEdit = (observable, oldValue, newValue) -> {
        if (newValue == null) {
            EditingTable.selectedTable = null;
            return;
        }
        EditingTable.selectedTable = "cinema";
        EditingTable.selectedIDTable = newValue.getId();
    };


    public static ChangeListener<?> getListener(String nameTable) {
        if (nameTable == null) return null;
        switch (nameTable.toLowerCase()) {
            case "cinema":
                return cinemaEdit;
            case "country":
                return countryEdit;
            case "session":
                return sessionEdit;
            case "film":
                return filmEdit;
            case "type_session":
                return typeSessionEdit;
            default:
                return null;
        }
    }


    public EditingTable() {

    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {

            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
            /**
             * @TODO Add to all Tables
             * @TOD
             *
             */
            if (selectedTable.equalsIgnoreCase("film") && getTableView().getColumns().indexOf(getTableColumn()) == 2) {
                List<String> choices = new ArrayList<>();
                HashMap<String, String> countriesHash = new HashMap<>();
                try {
                    ObservableList<Country> countries = Main.db.getCountryTable();
                    countries.forEach(data -> {
                        countriesHash.put(data.getName(), data.getId());
                        choices.add(data.getName());
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                dialog.setTitle("Выбор страны");
                dialog.setHeaderText("Пожалуйста выберите интересующую вами страну");
                dialog.setContentText("Вырерите страну:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    Debug.log(result.get());
                    commitEdit(countriesHash.get(result.get()));
                } else {
                    cancelEdit();
                }
            }
        }


    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener((arg0, arg1, arg2) -> {
            if (!arg2) {
                commitEdit(textField.getText());
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}

