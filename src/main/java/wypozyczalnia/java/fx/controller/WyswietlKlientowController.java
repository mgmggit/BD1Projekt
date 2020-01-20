package main.java.wypozyczalnia.java.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.wypozyczalnia.java.fx.database.WypozyczalniaHulajnog;
import main.java.wypozyczalnia.java.fx.model.Klient;

public class WyswietlKlientowController implements Initializable{

    private MainController mainController;

    private ObservableList<Klient> klientList = FXCollections.observableArrayList();

    @FXML
    public TableView<Klient> klientTable;

    @FXML
    private TableColumn<Klient, String> imieColumn;
    @FXML
    private TableColumn<Klient, String> nazwiskoColumn;
    @FXML
    private TableColumn<Klient, String> peselColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imieColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("imie"));
        nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("nazwisko"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("pesel"));
        klientTable.setItems(klientList);
    }

    @FXML
    public void pobierzDane() {
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        db.loadListaKlienci(klientList);
        loadDatabaseData(klientList);
    }

    private void loadDatabaseData(ObservableList<Klient> klientList) {
        klientTable = new TableView<Klient>();
        klientTable.setItems(klientList);
    }

    @FXML
    public void backToMenu() {
        goToMenu();
    }

    public void goToMenu() {
        mainController.loadMenuScreen();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}