package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Egzemplarze;
import model.Hulajnogi;
import model.WypozyczalniaHulajnog;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WyswietlEgzemplarzeController implements Initializable, ControlledScreen {

    ScreensController weController;

    @FXML
    private TableView<Egzemplarze> egzemplarzeTable;

    @FXML
    private TableColumn<Egzemplarze, Integer> hulajnogaIdColumn;

    @FXML
    private TableColumn<Egzemplarze, String> nrColumn;

    @FXML
    public void backButton() {
        goToMenu();
    }

    public void goToMenu() {
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        weController.ustawScreen(screen1ID);
    }

    @FXML
    private void pobierzDane() {
        hulajnogaIdColumn.setCellValueFactory(new PropertyValueFactory<>("hulajnogaId"));
        nrColumn.setCellValueFactory(new PropertyValueFactory<>("nr"));
        ObservableList<Egzemplarze> egzList = null;
        try {
            egzList = WypozyczalniaHulajnog.wyswietlEgzemplarze();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        egzemplarzeTable.setItems(egzList);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        weController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
