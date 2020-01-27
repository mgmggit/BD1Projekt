package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ListaUzytkownikow {

    private IntegerProperty uzytkownikId;
    private StringProperty login;
    private StringProperty haslo;

    public int getUzytkownikId() {
        return uzytkownikId.get();
    }

    public void setUzytkownikId(Integer uzytkownikId) {
        this.uzytkownikId.set(uzytkownikId);
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getHaslo() {
        return haslo.get();
    }

    public void setHaslo(String haslo) {
        this.haslo.set(haslo);
    }

    public ListaUzytkownikow() {
        this.uzytkownikId = new SimpleIntegerProperty();
        this.login = new SimpleStringProperty();
        this.haslo = new SimpleStringProperty();
    }

    public ListaUzytkownikow(int id, String login, String haslo) {
        this.uzytkownikId = new SimpleIntegerProperty(id);
        this.login = new SimpleStringProperty(login);
        this.haslo = new SimpleStringProperty(haslo);
    }
}
