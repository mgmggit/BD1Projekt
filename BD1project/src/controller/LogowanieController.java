package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.util.ResourceBundle;


public class LogowanieController implements Initializable, ControlledScreen {

    ScreensController logowanieController;

    @FXML
    private TextField login, haslo;

    public void loadDodawanieUzytkownika() { logowanieController.ustawScreen(screen12ID); }
    public void loadUsuwanieUzytkownika() { logowanieController.ustawScreen(screen13ID); }

    @FXML
    public void dodajUzytkownika() { loadDodawanieUzytkownika(); }

    @FXML
    public void usunUzytkownika() { loadUsuwanieUzytkownika(); }

    @FXML
    public void zaloguj(ActionEvent event){
        Button btn = (Button) event.getSource();
        String id = btn.getId();
        String log = login.getText();
        String has = haslo.getText();

        if (sprText(log, "login") && sprText(has, "haslo")) {
            if (WypozyczalniaHulajnog.sprUzytkownik(log, has)) {
                if (id.equals("zaloguj"))
                    logowanieController.ustawScreen(screen1ID);
            } else {
                warningAlert("Nie ma takiego użytkownika w bazie!");
            }
        }
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

    @FXML
    public void exit() {
        Platform.exit();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        logowanieController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
