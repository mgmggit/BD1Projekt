package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;;
import model.Klient;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WyswietlKlientowController implements Initializable, ControlledScreen {

    ScreensController wkController;

    @FXML
    private TableView<Klient> klientTable;

    @FXML
    private TableColumn<Klient, Integer> klientIdColumn;

    @FXML
    private TableColumn<Klient, String> nazwiskoColumn;

    @FXML
    private TableColumn<Klient, String> imieColumn;

    @FXML
    private TableColumn<Klient, String> peselColumn;


    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        wkController.ustawScreen(screen1ID);
    }

    @FXML
    private void pobierzDane() {
        klientIdColumn.setCellValueFactory(new PropertyValueFactory<>("klientId"));
        nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        ObservableList<Klient> klientList = null;
        try {
            klientList = WypozyczalniaHulajnog.wyswietlKlientow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        klientTable.setItems(klientList);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        wkController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
