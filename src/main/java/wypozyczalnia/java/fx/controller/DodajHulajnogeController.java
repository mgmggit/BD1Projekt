package main.java.wypozyczalnia.java.fx.controller;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import main.java.wypozyczalnia.java.fx.database.WypozyczalniaHulajnog;
import main.java.wypozyczalnia.java.fx.model.Hulajnogi;

public class DodajHulajnogeController {

    private MainController mainController;

    @FXML
    private TextField hulajnogaKlasa, hulajnogaRokNabycia;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        mainController.loadMenuScreen();
    }

    @FXML
    public void dodajHulajnoge(ActionEvent event) {
        String klasa = hulajnogaKlasa.getText();
        String rokNabycia = hulajnogaRokNabycia.getText();
        if (sprText(klasa, "klasa") && sprText(rokNabycia, "rok nabycia")) {
            potwierdzajacyDialog(klasa, rokNabycia);
        }
    }

    private void potwierdzajacyDialog(String klasa, String rokNabycia) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy te dane sa prawidlowe?\nKlasa: " + klasa + "\nRok nabycia: " + rokNabycia);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Dodawanie hulajnogi");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            Hulajnogi hulajnoga = new Hulajnogi(klasa, Integer.parseInt(rokNabycia));
            db.dodajHulajnoge(hulajnoga);
            goToMenu();
        }
    }


    //Spr czy rok nabycia jest dobrze wprowadzony
    public boolean sprRokNabycia(String prodRok) {
        if (prodRok != null) {
            if (czyNumer(prodRok)) {
                int year = Integer.parseInt(prodRok);
                if ((year < 2010) || (year > 2020)) {
                    String alert = "Blednie wprowadzony rok! Tylko hulajnogi nowsze niż z 2009.";
                    warningAlert(alert);
                    return false;
                }
            }
            else {
                String alert = "Rok musi byc liczba!";
                warningAlert(alert);
                return false;
            }
        } else {
            String alert = "Blednie wprowadzony rok!";
            warningAlert(alert);
            return false;
        } return true;
    }

    //Spr czy klasa nabycia jest wprowadzona
    public boolean sprText(String text, String label) {
        if ((text.equals("")) || (!czyText(text))) {
            String alert = "Pole " + label + " nie moze byc puste i musi skladaæ sie jedynie z liter!";
            warningAlert(alert);
            return false;
        } else { return true; }
    }

    //Czy text jest stringiem
    public boolean czyText(String text) {
        return text.chars().allMatch(Character::isLetter);
    }

    //Czy string jest numerem
    public boolean czyNumer(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isDigit(c)) {
                return false;
            }}
        return true;
    }

    public void warningAlert(String text) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}