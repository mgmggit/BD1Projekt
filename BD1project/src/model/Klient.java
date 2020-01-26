package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Klient {

    private IntegerProperty klientId;
    private StringProperty imie;
    private StringProperty nazwisko;
    private StringProperty pesel;


    public int getKlientId() {
        return klientId.get();
    }

    public void setKlientId(Integer klientId) {
        this.klientId.set(klientId);
    }

    public String getImie() {
        return imie.get();
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getPesel() {
        return pesel.get();
    }

    public void setPesel(String pesel) {
        this.pesel.set(pesel);
    }

    public Klient() {
        this.klientId = new SimpleIntegerProperty();
        this.imie = new SimpleStringProperty();
        this.nazwisko = new SimpleStringProperty();
        this.pesel = new SimpleStringProperty();
    }

    public Klient(int klientId, String imie, String nazwisko, String pesel) {
        this.klientId = new SimpleIntegerProperty(klientId);
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.pesel = new SimpleStringProperty(pesel);
    }
}