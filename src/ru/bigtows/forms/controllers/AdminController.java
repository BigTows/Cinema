package ru.bigtows.forms.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.forms.Form;
import ru.bigtows.table.Log;
import ru.bigtows.util.Debug;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bigtows on 10/04/2017.
 */
public class AdminController {
    @FXML
    private TabPane tabPane;

    private Tab addTab;

    @FXML
    private TableView TableLogs;

    @FXML
    public void initialize() {
        try {
            fillTabPane();
            addTab = paneAdd();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tabPane.getTabs().add(addTab);

    }

    public void fillTabPane() throws SQLException {
        ResultSet resultSet = Main.db.getUsers();
        while (resultSet.next()) {
            addUser(resultSet.getString(1));
        }
    }

    /**
     * add Tab with information about user
     *
     * @param name
     */
    public void addUser(String name) {
        Tab tab = new Tab(name);
        HBox hb = new HBox();
        hb.setPadding(new Insets(15, 12, 15, 12));
        hb.setSpacing(20);
        ResultSet grants = Main.db.getGrants(name);
        StringBuilder grantsText = new StringBuilder("Группа: ");
        try {
            while (grants.next()) {
                grantsText.append(grants.getString(1) + ", ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Label label = new Label(grantsText.toString());
        label.setWrapText(true);
        label.setMaxWidth(400);
        Button btn = new Button("Удалить");
        hb.getChildren().add(label);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Debug.log("[AdminPanel]: remove user " + name);
                if (Main.db.removeUser(name)) {
                    tabPane.getTabs().remove(tab);
                    tabPane.getTabs().remove(addTab);
                    tabPane.getTabs().add(addTab);
                }
            }
        });
        hb.getChildren().add(btn);
        tab.setContent(hb);

        tabPane.getTabs().add(tab);
    }


    public void onExitFromAdminPanel(ActionEvent actionEvent) {
        try {
            Form.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxml/Menu.fxml")), 650, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tab paneAdd() throws SQLException {
        Tab tab = new Tab("+");
        HBox hb = new HBox();
        TextField userName = new TextField();
        TextField password = new TextField();
        ComboBox comboBox = new ComboBox();
        ResultSet roles = Main.db.getRoles();
        while (roles.next()) {
            comboBox.getItems().addAll(roles.getString(1));
        }
        Button btn = new Button("Добавить");
        btn.setOnAction(event -> {
            Debug.log("[AdminPanel]: add user " + userName.getText());
            if (Main.db.addUser(userName.getText(), password.getText(), comboBox.getSelectionModel().getSelectedItem().toString())) {
                addUser(userName.getText());
                tabPane.getTabs().remove(addTab);
                tabPane.getTabs().add(addTab);
            }
        });
        hb.getChildren().add(userName);
        hb.getChildren().add(password);
        hb.getChildren().add(comboBox);
        hb.getChildren().add(btn);
        tab.setContent(hb);

        return tab;
    }

    public void onCreateBackup(ActionEvent actionEvent) {

    }

    public void onShowLogs(Event event) {
        try {
            Log.fillLog(TableLogs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
