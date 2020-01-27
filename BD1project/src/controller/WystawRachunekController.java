package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.WypozyczalniaHulajnog;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class WystawRachunekController implements Initializable, ControlledScreen {

    ScreensController wrController;

    @FXML
    public TextField wypozyczenieId, koszt;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        wrController.ustawScreen(screen1ID);
    }

    @FXML
    public void wystawRachunek(ActionEvent event) {
        String id = wypozyczenieId.getText();
            if (czyNumer(id, "Id wypozyczenia") && WypozyczalniaHulajnog.sprWypozyczenie(id)) {
                potwierdzajacyDialog(Integer.parseInt(id));
            } else {
                warningAlert("Nie ma wypozyczenie o takim id!");
            }
    }


    private void potwierdzajacyDialog(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz sfinalizować wypozyczenie?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Rachunek wydany!");
            int a = WypozyczalniaHulajnog.wystawRachunek(id);
            koszt.setText(String.valueOf(a));
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
        wrController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
