package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by bigtows on 29/03/2017.
 */
public class Cinema {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;


    public Cinema(String id, String name, String address) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
