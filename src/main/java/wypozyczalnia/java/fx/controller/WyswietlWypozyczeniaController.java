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
import main.java.wypozyczalnia.java.fx.model.ListaWypozyczen;

public class WyswietlWypozyczeniaController implements Initializable{

    private MainController mainController;

    private ObservableList<ListaWypozyczen> wypozyczeniaList = FXCollections.observableArrayList();

    @FXML
    public TableView<ListaWypozyczen> wypozyczeniaTable;

    @FXML
    private TableColumn<ListaWypozyczen, Integer> klientIdColumn;
    @FXML
    private TableColumn<ListaWypozyczen, String> nrRejestrColumn;
    @FXML
    private TableColumn<ListaWypozyczen, Integer> godzinaOdColumn;
    @FXML
    private TableColumn<ListaWypozyczen, Integer> godzinaDoColumn;
    @FXML
    private TableColumn<ListaWypozyczen, Integer> nrUslugiColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        klientIdColumn.setCellValueFactory(new PropertyValueFactory<ListaWypozyczen, Integer>("id"));
        nrRejestrColumn.setCellValueFactory(new PropertyValueFactory<>("nr rejestracyjny"));
        godzinaOdColumn.setCellValueFactory(new PropertyValueFactory<>("godzina wypozyczenia"));
        godzinaDoColumn.setCellValueFactory(new PropertyValueFactory<>("godzina zwrotu"));
        nrUslugiColumn.setCellValueFactory(new PropertyValueFactory<>("nr uslugi"));
        wypozyczeniaTable.setItems(wypozyczeniaList);
    }

    private void loadDatabaseData(ObservableList<ListaWypozyczen> wypozyczeniaList) {
        this.wypozyczeniaList = wypozyczeniaList;
        wypozyczeniaTable = new TableView<>();
        wypozyczeniaTable.setItems(wypozyczeniaList);
    }

    @FXML
    public void pobierzDane() {
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        db.loadListaWypozyczen(wypozyczeniaList);
        loadDatabaseData(wypozyczeniaList);
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