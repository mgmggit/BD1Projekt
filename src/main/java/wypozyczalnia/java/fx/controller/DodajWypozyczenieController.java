package main.java.wypozyczalnia.java.fx.controller;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.java.wypozyczalnia.java.fx.database.WypozyczalniaHulajnog;
import main.java.wypozyczalnia.java.fx.model.ListaWypozyczen;


public class DodajWypozyczenieController {

    private MainController mainController;

    @FXML
    private TextField idKlientaText, godzinaOdText, godzinaDoText, nrRejestrText, nrUslugiText;

    @FXML
    public void dodajWypozyczenie() {
        String idKlienta = idKlientaText.getText();
        String nrRejestr = nrRejestrText.getText();
        String godzinaOd = godzinaOdText.getText();
        String godzinaDo = godzinaDoText.getText();
        String nrUslugi = nrUslugiText.getText();


        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();

        if (db.sprKlient(Integer.parseInt(idKlienta))) {
            if (db.sprHulajnoga(nrRejestr)) {
                potwierdzajacyDialog(Integer.parseInt(idKlienta), nrRejestr, Integer.parseInt(godzinaOd), Integer.parseInt(godzinaDo), Integer.parseInt(nrUslugi));
            } else {
                warningAlert("Nie ma pojazdu o takim numerze rejestracyjnym!");
            }
        } else {
            warningAlert("Nie ma takiego klienta!");
        }
    }


    //Potwierdzenie danych i wysłanie do bazy
    private void potwierdzajacyDialog(int idklienta, String nrrejestr, int godzinaod, int godzinado, int nruslugi) {
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy chcesz dodac to wypozyczenie?\nWypozyczajacy (id): " + idklienta + " " + "\nHulajnoga wypozyczana: " + db.hulajnogaInfo(nrrejestr) + "\nNumer rejestracyjny hulajnogi: " + nrrejestr + "\nGodzina wypozyczenia: " + godzinaod + "Godzina zwrotu: " + godzinado + "\nNr uslugi: " + nruslugi);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ListaWypozyczen rent = new ListaWypozyczen(idklienta, nrrejestr);
            db.dodajWypozyczenie(rent);
            goToMenu();
        }
    }

    public void warningAlert(String text) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        mainController.loadMenuScreen();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}