package main.java.wypozyczalnia.java.fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MenuController {

    private MainController mainController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public void dodaj(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String id = btn.getId();
        System.out.println(id);
        FXMLLoader loader = null;
        switch (id) {
            case "dodajHulajnoge":
                loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/DodajHulajnoge.fxml"));
                break;
            case "dodajKlienta":
                loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/DodajKlienta.fxml"));
                break;
            case "dodajWypozyczenie":
                loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/DodajWypozyczenie.fxml"));
                break;
            case "swyswietlHulajnogi":
                loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/WyswietlHulajnogi.fxml"));
                break;
            case "wyswietlKlientow":
                loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/WyswietlKlientow.fxml"));
                break;
            case "wyswietlWypozyczenia":
                loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/WyswietlWypozyczenia.fxml"));
                break;
            default:
                System.out.println("Nie ma takiego przycisku");
                break;
        }
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainController.setScreen(pane);
        backToMenu(id, loader);
    }

    public void backToMenu(String id, FXMLLoader loader) {
        switch (id) {
            case "dodajHulajnoge":
                DodajHulajnogeController dodajHulajnogeController = loader.getController();
                dodajHulajnogeController.setMainController(mainController);
                break;
            case "dodajKlienta":
                DodajKlientaController dodajKlientaController = loader.getController();
                dodajKlientaController.setMainController(mainController);
                break;
            case "dodajWypozyczenie":
                DodajWypozyczenieController dodajWypozyczenieController = loader.getController();
                dodajWypozyczenieController.setMainController(mainController);
                break;
            case "wyswietlHulajnogi":
                WyswietlHulajnogiController wyswietlHulajnogiController = loader.getController();
                wyswietlHulajnogiController.setMainController(mainController);
                break;
            case "wyswietlKlientow":
                WyswietlKlientowController showClientsController = loader.getController();
                showClientsController.setMainController(mainController);
                break;
            case "wyswietlWypozyczenia":
                WyswietlWypozyczeniaController showRentsController = loader.getController();
                showRentsController.setMainController(mainController);
                break;
            default:
                System.out.println("Nie ma takiego przycisku");
                break;
        }
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void show() {
        System.out.println("Wyniki z bazy danych");
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}