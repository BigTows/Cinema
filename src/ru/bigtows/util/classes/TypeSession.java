package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by bigtows on 29/03/2017.
 */
public class TypeSession {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;


    public TypeSession(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }


    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
