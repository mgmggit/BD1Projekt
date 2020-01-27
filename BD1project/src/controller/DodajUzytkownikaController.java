package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.ListaUzytkownikow;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DodajUzytkownikaController implements Initializable, ControlledScreen {

    ScreensController duController;

    @FXML
    public void backButton() {
        goToLog();
    }

    public void goToLog() { loadLogScreen(); }

    public void loadLogScreen() {
        duController.ustawScreen(screen0ID);
    }


    @FXML
    private TextField uzytkownikId, login, haslo;

    @FXML
    public void dodajUzytkownika() {
        String id = uzytkownikId.getText();
        String log = login.getText();
        String has = haslo.getText();

        if (czyNumer(id) && sprText(log, "login") && sprText(has, "haslo")) {
            potwierdzajacyDialog(Integer.parseInt(id), log, has);
        }
    }
    //potwierdzenie danych i wyslanie do BD
    private void potwierdzajacyDialog(int id, String login, String haslo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy te dane sa prawidłowe?\nId: " + id + "\nLogin: " + login + "\nHaslo: " + haslo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Użytkownik zarejestrowany!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            ListaUzytkownikow uzytkownik = new ListaUzytkownikow(id, login, haslo);
            db.dodajUzytkownika(uzytkownik);
            goToLog();
        }
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

    public boolean sprText(String text, String label) {
        if (text.equals("")) {
            String alert = "Pole " + label + " nie moze być puste!";
            warningAlert(alert);
            return false;
        } else {
            return true;
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
        duController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
