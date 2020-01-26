package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Hulajnogi {

    private IntegerProperty hulajnogaId;
    private StringProperty klasa;
    private IntegerProperty rokNabycia;

    public int getHulajnogaId() {
        return hulajnogaId.get();
    }

    public void setHulajnogaId(Integer hulajnogaId) {
        this.hulajnogaId.set(hulajnogaId);
    }

    public String getKlasa() {
        return klasa.get();
    }

    public void setKlasa(String klasa) {
        this.klasa.set(klasa);
    }

    public int getRokNabycia() {
        return rokNabycia.get();
    }

    public void setRokNabycia(int rokNabycia) {
        this.rokNabycia.set(rokNabycia);
    }

    public Hulajnogi() {
        this.hulajnogaId = new SimpleIntegerProperty();
        this.klasa = new SimpleStringProperty();
        this.rokNabycia = new SimpleIntegerProperty();
    }

    public Hulajnogi(int hulajnogaId, String klasa, int rokNabycia) {
        this.hulajnogaId = new SimpleIntegerProperty(hulajnogaId);
        this.klasa = new SimpleStringProperty(klasa);
        this.rokNabycia = new SimpleIntegerProperty(rokNabycia);
    }
}