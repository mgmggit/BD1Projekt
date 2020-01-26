package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;



public class MainController implements  Initializable, ControlledScreen {

    ScreensController mainController;

    @FXML
    public void dodaj(ActionEvent event){
        Button btn = (Button) event.getSource();
        String id = btn.getId();
        System.out.println(id);
        switch (id) {
            case "dodajHulajnoge":
                mainController.ustawScreen(screen2ID);
                break;
            case "dodajKlienta":
                mainController.ustawScreen(screen3ID);
                break;
            case "dodajWypozyczenie":
                mainController.ustawScreen(screen4ID);
                break;
            case "wyswietlHulajnogi":
                mainController.ustawScreen(screen5ID);
                break;
            case "wyswietlKlientow":
                mainController.ustawScreen(screen6ID);
                break;
            case "wyswietlWypozyczenia":
                mainController.ustawScreen(screen7ID);
                break;
            case "usunHulajnoge":
                mainController.ustawScreen(screen8ID);
                break;
            case "usunKlienta":
                mainController.ustawScreen(screen9ID);
                break;
            case "usunWypozyczenie":
                mainController.ustawScreen(screen10ID);
                break;
            case "wystawRachunek":
                mainController.ustawScreen(screen11ID);
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

    @Override
    public void setScreenParent(ScreensController screenParent) {
        mainController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
