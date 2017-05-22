package ru.bigtows.forms.controllers;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.bigtows.Main;
import ru.bigtows.table.*;
import ru.bigtows.util.Debug;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static ru.bigtows.forms.Form.stage;

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
                grantsText.append(grants.getString(1)).append(", ");
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
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxml/Menu.fxml")), 650, 400));
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
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory =
                directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            XMLOutputFactory f = XMLOutputFactory.newInstance();
            try {
                XMLStreamWriter writer = null;
                try {
                    writer = f.createXMLStreamWriter(new FileWriter(selectedDirectory.getAbsolutePath() + "/backupCinemaDataBase.xml"));
                } catch (IOException e) {
                    Debug.log(e.getMessage());
                }
                if (writer == null) {
                    return;
                }
                writer = new IndentingXMLStreamWriter(writer);
                HashMap<String, String> data = new HashMap<>();
                writer.writeStartDocument();
                writer.writeStartElement("TABLES");
                startTable(writer, "country");
                for (Country country : Main.db.getCountryTable()) {
                    data.put("NAME", country.getName());
                    printRecord(writer, country.getId(), data);
                }
                writer.writeEndElement();
                startTable(writer, "film");
                for (Film film : Main.db.getFilmTable()) {
                    data.put("NAME", film.getName());
                    data.put("DURATION", film.getDuration());
                    data.put("COUNTRY", film.getIdC());
                    printRecord(writer, film.getId(), data);
                }
                writer.writeEndElement();
                startTable(writer, "cinema");
                for (Cinema cinema : Main.db.getCinemaTable()) {
                    data.put("NAME", cinema.getNameCinema());
                    data.put("ADDRESS", cinema.getAddressCinema());
                    printRecord(writer, cinema.getIdCinema(), data);
                }
                writer.writeEndElement();
                startTable(writer, "typeSession");
                for (TypeSession typeSession : Main.db.getTypeSessionTable()) {
                    data.put("NAME", typeSession.getName());
                    printRecord(writer, typeSession.getId(), data);
                }
                writer.writeEndElement();
                startTable(writer, "session");
                for (Session session : Main.db.getSessionTable()) {
                    data.put("CINEMA", session.getIdC());
                    data.put("DATE", session.getDate());
                    data.put("ROOM", session.getIdR());
                    data.put("TYPESESSION", session.getIdT());
                    data.put("FILM", session.getIdF());
                    printRecord(writer, session.getId(), data);
                }
                writer.writeEndElement();
                writer.writeEndDocument();
                writer.close();
            } catch (XMLStreamException e) {
                Debug.log(e.getMessage());
            }
        }
    }

    private void startTable(XMLStreamWriter writer, String nameTable) {
        try {
            writer.writeStartElement("TABLE");
            writer.writeAttribute("name", nameTable);
        } catch (XMLStreamException e) {
            Debug.log(e.getMessage());
        }

    }

    private void printRecord(XMLStreamWriter writer, String id, HashMap<String, String> dataMap) {
        try {
            writer.writeStartElement("RECORD");
            writer.writeAttribute("id", id);
            writeData(writer, dataMap);
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            Debug.log(e.getMessage());
        }
    }

    private void writeData(XMLStreamWriter writer, HashMap<String, String> dataMap) {
        dataMap.forEach((key, item) -> {
            try {
                writer.writeStartElement(key);
                writer.writeCharacters(item);
                writer.writeEndElement();
            } catch (XMLStreamException e) {
                Debug.log(e.getMessage());
            }
        });
        dataMap.clear();

    }

    public void onShowLogs(Event event) {
        try {
            Log.fillLog(TableLogs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
