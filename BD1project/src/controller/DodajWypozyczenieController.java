package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.WypozyczalniaHulajnog;
import model.ListaWypozyczen;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DodajWypozyczenieController implements Initializable, ControlledScreen {

    ScreensController dwController;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        dwController.ustawScreen(screen1ID);
    }

    @FXML
    private TextField  idWypozyczeniaText, idKlientaText, godzinaOdText, godzinaDoText, nrRejestrText, nrUslugiText;

    @FXML
    public void dodajWypozyczenie() {
        String idWypozyczenia = idWypozyczeniaText.getText();
        String idKlienta = idKlientaText.getText();
        String nrRejestr = nrRejestrText.getText();
        String godzinaOd = godzinaOdText.getText();
        String godzinaDo = godzinaDoText.getText();
        String nrUslugi = nrUslugiText.getText();


        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();

        if (db.sprKlient(Integer.parseInt(idKlienta))) {
            if (db.sprHulajnoga(nrRejestr)) {
                potwierdzajacyDialog(Integer.parseInt(idWypozyczenia), Integer.parseInt(idKlienta), nrRejestr, Integer.parseInt(godzinaOd), Integer.parseInt(godzinaDo), Integer.parseInt(nrUslugi));
            } else {
                warningAlert("Nie ma pojazdu o takim numerze rejestracyjnym!");
            }
        } else {
            warningAlert("Nie ma takiego klienta!");
        }
    }


    //Potwierdzenie danych i wysłanie do bazy
    private void potwierdzajacyDialog(int idwypozyczenia, int idklienta, String nrrejestr, int godzinaod, int godzinado, int nruslugi) {
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy chcesz dodac to wypozyczenie?\nWypozyczajacy (id): " + idklienta + "\nNumer rejestracyjny hulajnogi: " + nrrejestr + "\nGodzina wypozyczenia: " + godzinaod + " Godzina zwrotu: " + godzinado + "\nNr uslugi: " + nruslugi);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ListaWypozyczen rent = new ListaWypozyczen(idwypozyczenia, idklienta, nrrejestr, godzinaod, godzinado, nruslugi);
            db.dodajWypozyczenie(rent);
            goToMenu();
        }
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
        dwController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

