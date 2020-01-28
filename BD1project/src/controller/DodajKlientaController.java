package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.WypozyczalniaHulajnog;
import model.Klient;
import javafx.scene.control.Alert.AlertType;

public class DodajKlientaController implements Initializable, ControlledScreen {

    ScreensController dkController;

    @FXML
    private TextField klientId, imieKlienta, nazwiskoKlienta, peselKlienta;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        dkController.ustawScreen(screen1ID);
    }

    @FXML
    public void dodajKlienta() {
        String id = klientId.getText();
        String imie = imieKlienta.getText();
        String nazwisko = nazwiskoKlienta.getText();
        String pesel = peselKlienta.getText();

        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();

        if (sprText(imie, "imie") && sprText(nazwisko, "nazwisko") && sprPesel(pesel)) {
            if(db.sprKlient(Integer.parseInt(id))){
                warningAlert("Klient o takim id już istnieje!");
            } else {
                potwierdzajacyDialog(Integer.parseInt(id), imie, nazwisko, pesel);
            }
        }
    }

    //potwierdzenie danych i wyslanie do BD
    private void potwierdzajacyDialog(int id, String imie, String nazwisko, String pesel) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy te dane sa prawidłowe?\nImie: " + imie + "\nNazwisko: " + nazwisko + "\nPesel: " + pesel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Klient dodany!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            Klient klient = new Klient(id, imie, nazwisko, pesel);
            db.dodajKlienta(klient);
            goToMenu();
        }
    }

    //Spr czy nazwisko jest wprowadzone
    public boolean sprText(String text, String label) {
        if ((text.equals("")) || (!czyText(text))) {
            String alert = "Pole " + label + " nie moze być puste i musi skladać sie jedynie z liter!";
            warningAlert(alert);
            return false;
        } else {
            return true;
        }
    }

    //Spr czy pesel jest dobrze wprowadzony
    public boolean sprPesel(String pesel) {
        if ((!czyNumer(pesel)) || (pesel.length() != 11)) {
            String alert = "Podany zły numer PESEL!";
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
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public void warningAlert(String text) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        dkController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}