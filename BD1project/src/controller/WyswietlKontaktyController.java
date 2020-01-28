package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Kontakt;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WyswietlKontaktyController implements Initializable, ControlledScreen {

    ScreensController wtController;

    @FXML
    private TableView<Kontakt> kontaktyTable;

    @FXML
    private TableColumn<Kontakt, Integer> klientIdColumn;

    @FXML
    private TableColumn<Kontakt, String> telColumn;


    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        wtController.ustawScreen(screen1ID);
    }

    @FXML
    private void pobierzDane() {
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        klientIdColumn.setCellValueFactory(new PropertyValueFactory<>("klientId"));
        ObservableList<Kontakt> kontaktList = null;
        try {
            kontaktList = WypozyczalniaHulajnog.wyswietlKontakty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        kontaktyTable.setItems(kontaktList);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        wtController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
