package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ListaWypozyczen;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WyswietlWypozyczeniaController implements Initializable, ControlledScreen {

    ScreensController wwController;

    @FXML
    private TableView<ListaWypozyczen> wypozyczeniaTable;

    @FXML
    private TableColumn<ListaWypozyczen, Integer> wypozyczenieIdColumn;

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



    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        wwController.ustawScreen(screen1ID);
    }

    @FXML
    private void pobierzDane() {
        wypozyczenieIdColumn.setCellValueFactory(new PropertyValueFactory<>("wypozyczenieId"));
        klientIdColumn.setCellValueFactory(new PropertyValueFactory<>("klientId"));
        nrRejestrColumn.setCellValueFactory(new PropertyValueFactory<>("nrRejestr"));
        godzinaOdColumn.setCellValueFactory(new PropertyValueFactory<>("godzinaOd"));
        godzinaDoColumn.setCellValueFactory(new PropertyValueFactory<>("godzinaDo"));
        nrUslugiColumn.setCellValueFactory(new PropertyValueFactory<>("nrUslugi"));
        ObservableList<ListaWypozyczen> wypozyczeniaList = null;
        try {
            wypozyczeniaList = WypozyczalniaHulajnog.wyswietlWypozyczenia();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        wypozyczeniaTable.setItems(wypozyczeniaList);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        wwController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
