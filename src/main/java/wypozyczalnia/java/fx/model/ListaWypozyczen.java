package main.java.wypozyczalnia.java.fx.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ListaWypozyczen {

    private SimpleIntegerProperty klientId;
    private SimpleStringProperty nrRejestr;
    private SimpleIntegerProperty godzinaOd;
    private SimpleIntegerProperty godzinaDo;
    private SimpleIntegerProperty nrUslugi;

    public int getKlientId() {
        return klientId.get();
    }
    public void setKlientId(SimpleIntegerProperty klientId) {
        this.klientId = klientId;
    }
    public String getNrRejestr() {
        return nrRejestr.get();
    }
    public void setNrRejestr(SimpleStringProperty nrRejestr) {
        this.nrRejestr = nrRejestr;
    }
    public int getGodzinaOd() {
        return godzinaOd.get();
    }
    public void setGodzinaOd(SimpleIntegerProperty godzinaOd) {
        this.godzinaOd = godzinaOd;
    }
    public int getGodzinaDo() {
        return godzinaDo.get();
    }
    public void setGodzinaDo(SimpleIntegerProperty godzinaDo) {
        this.godzinaDo = godzinaDo;
    }
    public int getNrUslugi() {
        return nrUslugi.get();
    }
    public void setNrUslugi(SimpleIntegerProperty nrUslugi) {
        this.nrUslugi = nrUslugi;
    }

    public ListaWypozyczen(int klientId, String nrRejestr, int godzinaOd, int godzinaDo, int nrUslugi) {
        this.klientId = new SimpleIntegerProperty(klientId);
        this.nrRejestr = new SimpleStringProperty(nrRejestr);
        this.godzinaOd = new SimpleIntegerProperty(godzinaOd);
        this.godzinaDo = new SimpleIntegerProperty(godzinaDo);
        this.nrUslugi = new SimpleIntegerProperty(nrUslugi);

    }
    public ListaWypozyczen(int klientId, String nrRejestr) {
        this.klientId = new SimpleIntegerProperty(klientId);
        this.nrRejestr = new SimpleStringProperty(nrRejestr);
    }
}
