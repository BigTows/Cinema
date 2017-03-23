package ru.bigtows.forms.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ru.bigtows.Main;
import ru.bigtows.forms.Form;
import ru.bigtows.util.DataBase;

import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * Created by bigtows on 19/03/2017.
 */
public class LoginController {


    @FXML
    private TextField host;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Button Login;


    @FXML
    private void onClickLogin(MouseEvent event) throws Exception {
        Main.db = new DataBase(host.getText(), "Cinema", user.getText(), password.getText());
        if (!Main.db.getStatus()) {
            Alert alert = new Alert(ERROR);
            alert.setTitle("Ошибка подключения");
            alert.setHeaderText("Ошибка...");
            alert.setContentText("Проверьте данные входа!");
            alert.showAndWait();
        } else {
            Form.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxml/Menu.fxml")), 500, 300));
        }
    }
}
