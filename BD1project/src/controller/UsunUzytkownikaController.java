package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsunUzytkownikaController implements Initializable, ControlledScreen {

    ScreensController uuController;

    @FXML
    private TextField login;

    @FXML
    public void backButton() {
        goToLog();
    }

    public void goToLog() {
        loadLogScreen();
    }

    public void loadLogScreen() {
        uuController.ustawScreen(screen0ID);
    }

    @FXML
    public void usunUzytkownika() {
        String log = login.getText();
        if (sprText(log, "login") && WypozyczalniaHulajnog.sprLogin(log)) {
            potwierdzajacyDialog(log);
        } else {
            warningAlert("Nie ma użytkownika o takim loginie!");
        }
    }

    //potwierdzenie danych i wyslanie do BD
    private void potwierdzajacyDialog(String log) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz usunac uzytkownika o loginie " + log + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Uzytkownik usuniety!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            db.usunUzytkownika(log);
            goToLog();
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

    @Override
    public void setScreenParent(ScreensController screenParent) {
        uuController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
