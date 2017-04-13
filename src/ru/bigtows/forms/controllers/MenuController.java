package ru.bigtows.forms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.Form;
import ru.bigtows.util.Debug;
import ru.bigtows.util.classes.EditingTable;

import java.io.IOException;
import java.sql.SQLException;

import static ru.bigtows.util.classes.Cinema.fillCinema;
import static ru.bigtows.util.classes.Country.fillCountry;
import static ru.bigtows.util.classes.Film.fillFilm;
import static ru.bigtows.util.classes.Session.fillSession;
import static ru.bigtows.util.classes.TypeSession.fillTypeSession;

/**
 * Created by bigtows on 19/03/2017.
 */
public class MenuController {


    @FXML
    private ListView listtables;
    @FXML
    private TableView table;

    @FXML
    private MenuItem deleteSubMenu;

    @FXML
    private HBox hb;

    @FXML
    private MenuItem adminSubMenu;

    @FXML
    private MenuItem logout;

    @FXML
    public void initialize() {

        try {
            listtables.setItems(Main.db.getTables());
        } catch (SQLException e) {
            Debug.log("[Form Menu]: Update ListTables failed");
        }
        table.setEditable(true);
    }


    @FXML
    private void Test(MouseEvent event) {
        if (listtables.getFocusModel().getFocusedItem() == null) return;
        fillTable(listtables.getFocusModel().getFocusedItem().toString(), table, hb);
    }


    public static void fillTable(String nameTable, TableView table, HBox hb) {
        table.getColumns().clear();
        hb.getChildren().clear();
        switch (nameTable.toLowerCase()) {
            case "country": {
                try {
                    fillCountry(table, hb);
                    EditingTable.lastTable = nameTable;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "film": {

                try {
                    fillFilm(table, hb);
                    EditingTable.lastTable = nameTable;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "cinema": {
                try {
                    fillCinema(table, hb);
                    EditingTable.lastTable = nameTable;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "type_session": {
                try {
                    fillTypeSession(table, hb);
                    EditingTable.lastTable = nameTable;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "session": {
                try {
                    fillSession(table, hb);
                    EditingTable.lastTable = nameTable;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    public void onClickLogoutItem(ActionEvent actionEvent) {
        Main.db.destroy();
        try {
            Form.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxml/Login.fxml")), 500, 300));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickAdminItem(ActionEvent actionEvent) {
        if (Main.db.isAdmin()) {
            try {
                Form.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxml/Admin.fxml")), 650, 400));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Нет прав!");
            alert.setContentText("У вас нет прав для доступа к AdminPanel.");
            alert.show();
        }
    }

    public void onCkickDeleteItem(ActionEvent actionEvent) {
        if (EditingTable.selectedTable == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Сначало выделите запись!");
            alert.setContentText("Для того чтобы удалить запись, вам нужно сначало выделить (нажать) на запись, которую требуется удалить.");
            alert.show();
        } else {
            switch (EditingTable.selectedTable.toLowerCase()) {
                case "film":
                    Main.db.removeFilm(EditingTable.selectedIDTable);

                    break;
                case "cinema":
                    Main.db.removeCinema(EditingTable.selectedIDTable);

                    break;
                case "session":
                    Main.db.removeSession(EditingTable.selectedIDTable);

                    break;
                case "type_session":
                    Main.db.removeTypeSession(EditingTable.selectedIDTable);

                    break;
                case "country":
                    Main.db.removeCountry(EditingTable.selectedIDTable);

                    break;
            }
            fillTable(EditingTable.selectedTable.toLowerCase(), table, hb);
            EditingTable.selectedTable = null;
        }
    }

}
