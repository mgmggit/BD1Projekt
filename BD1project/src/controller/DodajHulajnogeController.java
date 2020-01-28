package controller;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Hulajnogi;
import model.WypozyczalniaHulajnog;



public class DodajHulajnogeController implements Initializable, ControlledScreen {

    ScreensController dhController;

    @FXML
    private TextField hulajnogaId, hulajnogaKlasa, hulajnogaRokNabycia;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        dhController.ustawScreen(screen1ID);
    }

    @FXML
    public void dodajHulajnoge(ActionEvent event) {
        String id = hulajnogaId.getText();
        String klasa = hulajnogaKlasa.getText();
        String rokNabycia = hulajnogaRokNabycia.getText();
        if (sprText(klasa, "klasa") && czyRokOk(rokNabycia, "Rok nabycia") && czyNumer(id, "id")) {
            if(WypozyczalniaHulajnog.sprHid(Integer.parseInt(id))){
                warningAlert("Już istnieje hulajnoga o danym id!");
            } else {
                potwierdzajacyDialog(id, klasa, rokNabycia);
            }
        }
    }


    private void potwierdzajacyDialog(String id, String klasa, String rokNabycia) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setContentText("Czy te dane sa prawidlowe?\nKlasa: " + klasa + "\nRok nabycia: " + rokNabycia);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Hulajnoga dodana!");
            WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
            Hulajnogi hulajnoga = new Hulajnogi(Integer.parseInt(id), klasa, Integer.parseInt(rokNabycia));
            db.dodajHulajnoge(hulajnoga);
            goToMenu();
        }
    }

    //Spr czy klasa jest wprowadzona poprawnie
    public boolean sprText(String text, String label) {
        if ((text.equals("")) || (!czyText(text))) {
            String alert = "Pole " + label + " nie moze byc puste i musi skladac sie jedynie z liter!";
            warningAlert(alert);
            return false;
        } else { return true; }
    }

    //Czy text jest stringiem
    public boolean czyText(String text) {
        return text.chars().allMatch(Character::isLetter);
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

    public boolean czyRokOk(String text, String label) {
        if ((Integer.parseInt(text))<2000 || (Integer.parseInt(text))>2020) {
            String alert = label + " musi być co najmniej 2000!";
            warningAlert(alert);
            return false;
        } else { return true; }
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
        dhController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
