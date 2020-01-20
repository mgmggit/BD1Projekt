package main.java.wypozyczalnia.java.fx.controller;


import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import main.java.wypozyczalnia.java.fx.database.WypozyczalniaHulajnog;
import main.java.wypozyczalnia.java.fx.model.Klient;
import javafx.scene.control.Alert.AlertType;

public class DodajKlientaController {

    private MainController mainController;

    @FXML
    private TextField imieKlienta, nazwiskoKlienta, peselKlienta;

    @FXML
    public void backButton() {
        goToMenu();
    }

    @FXML
    public void dodajKlienta() {
        String imie = imieKlienta.getText();
        String nazwisko = nazwiskoKlienta.getText();
        String pesel = peselKlienta.getText();

        if(sprText(imie, "imie") && sprText(nazwisko, "nazwisko") && sprPesel(pesel)) {
            potwierdzajacyDialog(imie, nazwisko, pesel);
        }
    }

    //potwierdzenie danych i wyslanie do BD
    private void potwierdzajacyDialog(String imie, String nazwisko, String pesel) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy te dane sa prawidlowe?\nImie: " + imie + "\nNazwisko: " + nazwisko + "\nPesel: " + pesel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Dodawanie klienta");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            Klient klient = new Klient(imie, nazwisko, pesel);
            db.dodajKlienta(klient);
            goToMenu();
        }
    }

    //Spr czy nazwisko jest wprowadzone
    public boolean sprText(String text, String label) {
        if ((text.equals("")) || (!czyText(text))) {
            String alert = "Pole " + label + " nie moze byæ puste i musi skladac sie jedynie z liter!";
            warningAlert(alert);
            return false;
        } else { return true; }
    }

    //Spr czy pesel jest dobrze wprowadzony
    public boolean sprPesel(String pesel) {
        if ((!czyNumer(pesel)) || (pesel.length() != 11)) {
            String alert = "Podany zly numer PESEL!";
            warningAlert(alert);
            return false;
        } else {
            return true;
        }
    }

    //Czy string jest tekstem
    public boolean czyText(String text) {
        return text.chars().allMatch(Character::isLetter);
    }

    //Czy string jest numerem
    public boolean czyNumer(String num) {
        char[] chars = num.toCharArray();
        for (char c : chars) {
            if(!Character.isDigit(c)) {
                return false;
            }} return true;
    }

    public void warningAlert(String text) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void goToMenu() {
        mainController.loadMenuScreen();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
