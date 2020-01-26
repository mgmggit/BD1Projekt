package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Hulajnogi;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WyswietlHulajnogiController implements Initializable, ControlledScreen {

    ScreensController whController;

    @FXML
    private TableView<Hulajnogi> hulajnogiTable;

    @FXML
    private TableColumn<Hulajnogi, Integer> hulajnogaIdColumn;

    @FXML
    private TableColumn<Hulajnogi, String> klasaColumn;

    @FXML
    private TableColumn<Hulajnogi, Integer> rokNabyciaColumn;


    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        whController.ustawScreen(screen1ID);
    }

    @FXML
    private void pobierzDane() {
        hulajnogaIdColumn.setCellValueFactory(new PropertyValueFactory<>("hulajnogaId"));
        rokNabyciaColumn.setCellValueFactory(new PropertyValueFactory<>("rokNabycia"));
        klasaColumn.setCellValueFactory(new PropertyValueFactory<>("klasa"));
        ObservableList<Hulajnogi> hulajnogaList = null;
        try {
            hulajnogaList = WypozyczalniaHulajnog.wyswietlHulajnogi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hulajnogiTable.setItems(hulajnogaList);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        whController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
