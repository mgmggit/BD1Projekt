package controller;

public interface ControlledScreen {

    String screen0ID = "Logowanie";
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
    String screen12ID = "Dodawanie Uzytkownika";
    String screen13ID = "Usuwanie Uzytkownika";
    String screen14ID = "Dodawanie Egzemplarza";
    String screen15ID = "Wyswietlanie Egzemplarzy";
    String screen16ID = "Usuwanie Egzemplarza";
    String screen17ID = "Dodawanie Kontaktu";
    String screen18ID = "Wyswietlanie Kontaktów";
    String screen19ID = "Usuwanie Kontaktu";


    void setScreenParent(ScreensController screenPage);
}
