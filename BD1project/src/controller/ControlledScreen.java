package controller;

public interface ControlledScreen {

    String screen1ID = "Menu";
    String screen2ID = "Dodawanie Hulajnogi";
    String screen3ID = "Dodawanie Klienta";
    String screen4ID = "Dodawanie Wypozyczenia";
    String screen5ID = "Wywwietlanie Hulajnog";
    String screen6ID = "Wyswietlanie Klientow";
    String screen7ID = "Wyswietlanie Wypozyczen";
    String screen8ID = "Usuwanie Hulajnog";
    String screen9ID = "Usuwanie Klientow";
    String screen10ID = "Usuwanie Wypozyczen";
    String screen11ID = "Wystawianie Rachunku";

    void setScreenParent(ScreensController screenPage);
}
