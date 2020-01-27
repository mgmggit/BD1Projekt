package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ListaWypozyczen {

    private IntegerProperty wypozyczenieId;
    private IntegerProperty klientId;
    private StringProperty nrRejestr;
    private IntegerProperty godzinaOd;
    private IntegerProperty godzinaDo;
    private IntegerProperty nrUslugi;


    public int getWypozyczenieId() {
        return wypozyczenieId.get();
    }

    public void setWypozyczenieId(Integer wypozyczenieId) {
        this.wypozyczenieId.set(wypozyczenieId);
    }

    public int getKlientId() {
        return klientId.get();
    }

    public void setKlientId(Integer klientId) {
        this.klientId.set(klientId);
    }

    public String getNrRejestr() {
        return nrRejestr.get();
    }

    public void setNrRejestr(String nrRejestr) {
        this.nrRejestr.set(nrRejestr);
    }

    public int getGodzinaOd() {
        return godzinaOd.get();
    }

    public void setGodzinaOd(Integer godzinaOd) {
        this.godzinaOd.set(godzinaOd);
    }

    public int getGodzinaDo() {
        return godzinaDo.get();
    }

    public void setGodzinaDo(Integer godzinaDo) {
        this.godzinaDo.set(godzinaDo);
    }

    public int getNrUslugi() {
        return nrUslugi.get();
    }

    public void setNrUslugi(Integer nrUslugi) {
        this.nrUslugi.set(nrUslugi);
    }

    public ListaWypozyczen() {
        this.wypozyczenieId = new SimpleIntegerProperty();
        this.klientId = new SimpleIntegerProperty();
        this.nrRejestr = new SimpleStringProperty();
        this.godzinaOd = new SimpleIntegerProperty();
        this.godzinaDo = new SimpleIntegerProperty();
        this.nrUslugi = new SimpleIntegerProperty();
    }

    public ListaWypozyczen(int wypozyczenieId, int klientId, String nrRejestr, int godzinaOd, int godzinaDo, int nrUslugi) {
        this.wypozyczenieId = new SimpleIntegerProperty(wypozyczenieId);
        this.klientId = new SimpleIntegerProperty(klientId);
        this.nrRejestr = new SimpleStringProperty(nrRejestr);
        this.godzinaOd = new SimpleIntegerProperty(godzinaOd);
        this.godzinaDo = new SimpleIntegerProperty(godzinaDo);
        this.nrUslugi = new SimpleIntegerProperty(nrUslugi);
    }

}
