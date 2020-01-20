package main.java.wypozyczalnia.java.fx.model;

import javafx.beans.property.SimpleStringProperty;

public class Klient {

    private SimpleStringProperty imie;
    private SimpleStringProperty nazwisko;
    private SimpleStringProperty pesel;

    public String getImie() {
        return imie.get();
    }
    public void setName(SimpleStringProperty imie) {
        this.imie = imie;
    }
    public String getNazwisko() {
        return nazwisko.get();
    }
    public void setSurname(SimpleStringProperty nazwisko) {
        this.nazwisko = nazwisko;
    }
    public String getPesel() {
        return pesel.get();
    }
    public void setPesel(SimpleStringProperty pesel) {
        this.pesel = pesel;
    }

    public Klient(String imie, String nazwisko, String pesel) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.pesel = new SimpleStringProperty(pesel);
    }

}
