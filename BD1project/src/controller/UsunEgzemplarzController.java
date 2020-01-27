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


public class UsunEgzemplarzController implements Initializable, ControlledScreen {

    ScreensController ueController;

    @FXML
    private TextField nr;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        ueController.ustawScreen(screen1ID);
    }

    @FXML
    public void usunEgzemplarz() {
        String nrR = nr.getText();
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        if (sprText(nrR, "nr rejestracyjny") && db.sprHulajnoga(nrR)) {
            potwierdzajacyDialog(nrR);
        } else {
            warningAlert("Nie ma hulajnogi o takiej rejestracji!");
        }
    }

    //potwierdzenie danych i wyslanie do BD
    private void potwierdzajacyDialog(String nr) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz usunac hulajnoge " + nr + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Egzemplarz usuniety!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            db.usunEgzemplarz(nr);
            goToMenu();
        }
    }

    public boolean sprText(String text, String label) {
        if (text.equals("")) {
            String alert = "Pole " + label + " nie może być puste!";
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
        ueController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
