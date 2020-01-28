package controller;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Egzemplarze;
import model.Hulajnogi;
import model.WypozyczalniaHulajnog;



public class DodajEgzemplarzController implements Initializable, ControlledScreen {

    ScreensController deController;

    @FXML
    private TextField hulajnogaId, nr;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        deController.ustawScreen(screen1ID);
    }

    @FXML
    public void dodajEgzemplarz(ActionEvent event) {
        String id = hulajnogaId.getText();
        String nrR = nr.getText();
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        if (sprText(nrR, "nr rejestracyjny") && czyNumer(id, "id") && db.sprHulajnogaId(Integer.parseInt(id))) {
            if(!db.sprHulajnoga(nrR)){
                potwierdzajacyDialog(id, nrR);

            } else {
                warningAlert("Ten nr rejestracyjny jest już zajęty !");
            }
        } else {
            warningAlert("Nie ma hulajnogi o takim id !");
        }
    }



    private void potwierdzajacyDialog(String id, String nr) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy te dane sa prawidlowe?\nId: " + id + "\nRejestracja: " + nr);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Egzemplarz dodany!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            Egzemplarze egzemplarz = new Egzemplarze(nr, Integer.parseInt(id));
            db.dodajEgzemplarz(egzemplarz);
            goToMenu();
        }
    }

    public boolean sprText(String text, String label) {
        if (text.equals("")) {
            String alert = "Pole " + label + " nie moze byc puste !";
            warningAlert(alert);
            return false;
        } else { return true; }
    }


    public boolean czyNumer(String text, String label) {
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if(!Character.isDigit(c)) {
                String alert = "Pole " + label + " nie moze byc puste i musi skladac sie jedynie z cyfr!";
                warningAlert(alert);
                return false;
            }}
        return true;
    }

    public void warningAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        deController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
