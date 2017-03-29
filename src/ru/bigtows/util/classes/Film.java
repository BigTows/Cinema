package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by bigtows on 29/03/2017.
 */
public class Film {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty duration;
    private final SimpleStringProperty idC;


    public Film(String id, String name, String duration, String id_country) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.duration = new SimpleStringProperty(duration);
        this.idC = new SimpleStringProperty(id_country);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public String getDuration() {
        return duration.get();
    }

    public String getIdC() {
        return idC.get();
    }


    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public void setCountry(String country) {
        this.idC.set(country);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
