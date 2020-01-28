package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kontakt {

    private IntegerProperty klientId;
    private StringProperty tel;

    public int getKlientId() {
        return klientId.get();
    }

    public void setKlientId(Integer klientId) {
        this.klientId.set(klientId);
    }

    public String getTel() {
        return tel.get();
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public Kontakt() {
        this.klientId = new SimpleIntegerProperty();
        this.tel = new SimpleStringProperty();
    }

    public Kontakt(int klientId, String tel) {
        this.klientId = new SimpleIntegerProperty(klientId);
        this.tel = new SimpleStringProperty(tel);
    }

}
