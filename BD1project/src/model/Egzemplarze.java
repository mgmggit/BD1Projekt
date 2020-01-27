package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Egzemplarze {

    private IntegerProperty hulajnogaId;
    private StringProperty nr;

    public int getHulajnogaId() {
        return hulajnogaId.get();
    }

    public void setHulajnogaId(Integer hulajnogaId) {
        this.hulajnogaId.set(hulajnogaId);
    }

    public String getNr() {
        return nr.get();
    }

    public void setNr(String nr) {
        this.nr.set(nr);
    }

    public Egzemplarze() {
        this.nr = new SimpleStringProperty();
        this.hulajnogaId = new SimpleIntegerProperty();
    }

    public Egzemplarze(String nr, int id) {
        this.nr = new SimpleStringProperty(nr);
        this.hulajnogaId = new SimpleIntegerProperty(id);
    }
}
