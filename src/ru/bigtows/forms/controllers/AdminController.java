package ru.bigtows.forms.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import ru.bigtows.Main;
import ru.bigtows.util.Debug;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bigtows on 10/04/2017.
 */
public class AdminController {
    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        Tab tab = new Tab("+");
        HBox hb = new HBox();
        TextField userName = new TextField();
        TextField password = new TextField();
        ComboBox comboBox = new ComboBox();
        Button btn = new Button("Добавить");
        try {
            fillTabPane();
            ResultSet roles = Main.db.getRoles();
            while (roles.next()) {
                comboBox.getItems().addAll(roles.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Debug.log("[AdminPanel]: add user " + userName.getText());
                Main.db.addUser(userName.getText(), password.getText(), comboBox.getSelectionModel().getSelectedItem().toString());
                addUser(userName.getText());
            }
        });
        hb.getChildren().add(userName);
        hb.getChildren().add(password);
        hb.getChildren().add(comboBox);
        hb.getChildren().add(btn);
        tab.setContent(hb);

        tabPane.getTabs().add(tab);

    }

    public void fillTabPane() throws SQLException {
        ResultSet resultSet = Main.db.getUsers();
        while (resultSet.next()) {
            addUser(resultSet.getString(1));
        }
    }

    public void addUser(String name) {
        Tab tab = new Tab(name);
        HBox hb = new HBox();
        ResultSet grants = Main.db.getGrants(name);
        StringBuilder grantsText = new StringBuilder();
        try {
            while (grants.next()) {
                grantsText.append(grants.getString(1) + " ");
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
                Main.db.removeUser(name);
                tabPane.getTabs().remove(tab);
            }
        });
        hb.getChildren().add(btn);
        tab.setContent(hb);

        tabPane.getTabs().add(tab);
    }


}
