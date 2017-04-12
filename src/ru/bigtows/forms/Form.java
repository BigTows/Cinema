package ru.bigtows.forms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by bigtows on 19/03/2017.
 */
public class Form extends Application {

    public static Stage stage = null;
    public static String fileForm = "fxml/Login.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(this.fileForm));
        Scene scene = new Scene(root, 500, 150);
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    public static void init(String[] args) {
        launch(args);
    }
}
