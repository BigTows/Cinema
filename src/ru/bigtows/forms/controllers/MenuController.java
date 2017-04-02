package ru.bigtows.forms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.util.Debug;

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
    private HBox hb;
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
        fillTable(listtables.getFocusModel().getFocusedItem().toString(), table, hb);
    }

    public static void fillTable(String nameTable, TableView table, HBox hb) {
        table.getColumns().clear();
        hb.getChildren().clear();
        switch (nameTable.toLowerCase()) {
            case "country": {
                try {
                    fillCountry(table, hb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "film": {

                try {
                    fillFilm(table, hb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "cinema": {
                try {
                    fillCinema(table, hb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "type_session": {
                try {
                    fillTypeSession(table, hb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "session": {
                try {
                    fillSession(table, hb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
