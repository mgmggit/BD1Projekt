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


public class UsunKontaktController implements Initializable, ControlledScreen {

    ScreensController utController;

    @FXML
    private TextField tel;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        utController.ustawScreen(screen1ID);
    }

    @FXML
    public void usunKontakt() {
        String nr = tel.getText();
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        if (czyNumer(nr, "telefon") &&  db.sprTel(nr)) {
            potwierdzajacyDialog(nr);
        } else {
            warningAlert("Nie ma takiego nr telefonu w bazie!");
        }
    }

    //potwierdzenie danych i wyslanie do BD
    private void potwierdzajacyDialog(String nr) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz usunąć nr " + nr + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Kontakt usuniety!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            db.usunKontakt(nr);
            goToMenu();
        }
    }

    //Czy string jest numerem
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
        utController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
