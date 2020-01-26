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


public class UsunWypozyczenieController implements Initializable, ControlledScreen {

    ScreensController uwController;

    @FXML
    public TextField wypozyczenieId;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        uwController.ustawScreen(screen1ID);
    }

    @FXML
    public void usunWypozyczenie() {
        String id = wypozyczenieId.getText();
        if (czyNumer(id, "Id wypozyczenia")) {
            potwierdzajacyDialog(id);
        }
    }

    //potwierdzenie danych i wyslanie do BD
    private void potwierdzajacyDialog(String id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz usunac wypozyczenie o id =" + id + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Wypozyczenie usuniete!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            db.usunWypozyczenie(Integer.parseInt(id));
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
        uwController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}