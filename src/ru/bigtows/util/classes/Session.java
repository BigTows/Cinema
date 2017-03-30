package ru.bigtows.util.classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by bigtows on 30/03/2017.
 */
public class Session {
    private final SimpleStringProperty id;
    private final SimpleStringProperty idR;
    private final SimpleStringProperty idF;
    private final SimpleStringProperty idC;
    private final SimpleStringProperty idT;
    private final SimpleStringProperty date;


    public Session(String id, String idR, String idF, String idC, String idT, String date) {
        this.id = new SimpleStringProperty(id);
        this.idR = new SimpleStringProperty(idR);
        this.idF = new SimpleStringProperty(idF);
        this.idC = new SimpleStringProperty(idC);
        this.idT = new SimpleStringProperty(idT);
        this.date = new SimpleStringProperty(date);
    }

    public String getIdR() {
        return idR.get();
    }

    public String getIdF() {
        return idF.get();
    }

    public String getIdC() {
        return idC.get();
    }

    public String getIdT() {
        return idT.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setIdR(String id) {
        this.idR.set(id);
    }

    public void setIdF(String id) {
        this.idF.set(id);
    }

    public void setIdC(String id) {
        this.idC.set(id);
    }

    public void setIdT(String id) {
        this.idT.set(id);
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
