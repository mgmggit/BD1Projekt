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
import main.java.wypozyczalnia.java.fx.model.Hulajnogi;

public class WyswietlHulajnogiController implements Initializable{


    private MainController mainController;
    private ObservableList<Hulajnogi> hulajnogaList = FXCollections.observableArrayList();

    @FXML
    private TableView<Hulajnogi> hulajnogiTable;

    @FXML
    private TableColumn<Hulajnogi, String> klasaColumn;

    @FXML
    private TableColumn<Hulajnogi, Integer> rokNabyciaColumn;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        klasaColumn.setCellValueFactory(new PropertyValueFactory<Hulajnogi, String>("klasa"));
        rokNabyciaColumn.setCellValueFactory(new PropertyValueFactory<Hulajnogi, Integer>("rokNabycia"));
        hulajnogiTable.setItems(hulajnogaList);
    }

    private void loadDatabaseData(ObservableList<Hulajnogi> hulajnogaList) {
        hulajnogiTable = new TableView<Hulajnogi>();
        hulajnogiTable.setItems(hulajnogaList);
        hulajnogiTable.setVisible(false);
        hulajnogiTable.setVisible(true);
    }

    @FXML
    private void pobierzDane() {
        WypozyczalniaHulajnog db = new WypozyczalniaHulajnog();
        db.loadListaHulajnogi(hulajnogaList);
        loadDatabaseData(hulajnogaList);
    }

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        mainController.loadMenuScreen();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}