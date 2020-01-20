package main.java.wypozyczalnia.java.fx.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Hulajnogi {

    private SimpleStringProperty klasa;
    private SimpleIntegerProperty rokNabycia;

    public String getKlasa() {
        return klasa.get();
    }
    public void setKlasa(SimpleStringProperty klasa) {
        this.klasa = klasa;
    }
    public int getRokNabycia() {
        return rokNabycia.get();
    }
    public void setRokNabycia(SimpleIntegerProperty rokNabycia) {
        this.rokNabycia = rokNabycia;
    }

    public Hulajnogi(String klasa, int rokNabycia) {
        this.klasa = new SimpleStringProperty(klasa);
        this.rokNabycia = new SimpleIntegerProperty(rokNabycia);
    }
}