package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.Kontakt;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DodajKontaktController implements Initializable, ControlledScreen {

    ScreensController dtController;

    @FXML
    private TextField klientId, tel;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        dtController.ustawScreen(screen1ID);
    }

    @FXML
    public void dodajKontakt(ActionEvent event) {
        String id = klientId.getText();
        String nrT = tel.getText();
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        if (czyNumer(nrT, "nr telefonu") && czyNumer(id, "id") && db.sprKlient(Integer.parseInt(id))) {
                potwierdzajacyDialog(id, nrT);
        } else {
            warningAlert("Nie ma takiego klienta");
        }
    }


    private void potwierdzajacyDialog(String id, String nr) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy te dane sa prawidlowe?\nId: " + id + "\nTelefon: " + nr);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Kontakt dodany!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            Kontakt kontakt = new Kontakt(Integer.parseInt(id), nr);
            db.dodajKontakt(kontakt);
            goToMenu();
        }
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
        dtController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
