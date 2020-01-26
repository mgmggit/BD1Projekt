import controller.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static String screen1ID = "Menu";
    public static String screen1File = "/view/MenuScreen.fxml";
    public static String screen2ID = "Dodawanie Hulajnogi";
    public static String screen2File = "/view/DodajHulajnoge.fxml";
    public static String screen3ID = "Dodawanie Klienta";
    public static String screen3File = "/view/DodajKlienta.fxml";
    public static String screen4ID = "Dodawanie Wypozyczenia";
    public static String screen4File = "/view/DodajWypozyczenie.fxml";
    public static String screen5ID = "Wywwietlanie Hulajnog";
    public static String screen5File = "/view/WyswietlHulajnogi.fxml";
    public static String screen6ID = "Wyswietlanie Klientow";
    public static String screen6File = "/view/WyswietlKlientow.fxml";
    public static String screen7ID = "Wyswietlanie Wypozyczen";
    public static String screen7File = "/view/WyswietlWypozyczenia.fxml";
    public static String screen8ID = "Usuwanie Hulajnog";
    public static String screen8File = "/view/UsunHulajnoge.fxml";
    public static String screen9ID = "Usuwanie Klientow";
    public static String screen9File = "/view/UsunKlienta.fxml";
    public static String screen10ID = "Usuwanie Wypozyczen";
    public static String screen10File = "/view/UsunWypozyczenie.fxml";
    public static String screen11ID = "Wystawianie Rachunku";
    public static String screen11File = "/view/WystawRachunek.fxml";

    @Override
    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        mainContainer.zaladujScreen(Main.screen1ID, Main.screen1File);
        mainContainer.zaladujScreen(Main.screen2ID, Main.screen2File);
        mainContainer.zaladujScreen(Main.screen3ID, Main.screen3File);
        mainContainer.zaladujScreen(Main.screen4ID, Main.screen4File);
        mainContainer.zaladujScreen(Main.screen5ID, Main.screen5File);
        mainContainer.zaladujScreen(Main.screen6ID, Main.screen6File);
        mainContainer.zaladujScreen(Main.screen7ID, Main.screen7File);
        mainContainer.zaladujScreen(Main.screen8ID, Main.screen8File);
        mainContainer.zaladujScreen(Main.screen9ID, Main.screen9File);
        mainContainer.zaladujScreen(Main.screen10ID, Main.screen10File);
        mainContainer.zaladujScreen(Main.screen11ID, Main.screen11File);

        mainContainer.ustawScreen(Main.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Wypozyczalnia hulajnog");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
