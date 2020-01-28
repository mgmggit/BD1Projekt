package model;

import controller.WystawRachunekController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Observable;


public class WypozyczalniaHulajnog {
    private static final String url = "jdbc:postgresql://localhost:5431/u8grzesiak";
    private static final String user = "u8grzesiak";
    private static String password = "8grzesiak";

    private PreparedStatement prepStmt;
    private ResultSet rs;

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public void dodajKlienta(Klient klient) {
        String SQL = "INSERT INTO klienci(klient_id, imie, nazwisko, pesel) VALUES (?, ?, ?, ?);";
        try {Connection conn = connect();
            prepStmt = conn.prepareStatement(SQL);
            prepStmt.setInt(1, klient.getKlientId());
            prepStmt.setString(2, klient.getImie());
            prepStmt.setString(3, klient.getNazwisko());
            prepStmt.setString(4, klient.getPesel());

            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem klienta do BD");
            e.printStackTrace();
        }
    }

    public static void usunKlienta(int klientId) {
        String SQL = "DELETE FROM klienci WHERE klient_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, klientId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z usunieciem klienta z BD");
            e.printStackTrace();
        }
    }

    public static ObservableList<Klient> wyswietlKlientow() throws SQLException {
        String SQL = "SELECT * FROM klienci;";
        try {Connection conn = connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            ObservableList<Klient> klList = getKlientObjects(rs);
            return klList;
        } catch (SQLException e) {
            System.out.println("Problem z dostaniem danych klientow z BD" + e);
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<Klient> getKlientObjects(ResultSet rs) throws SQLException {
        try {
            ObservableList<Klient> klList = FXCollections.observableArrayList();
            while (rs.next()){
                Klient klient = new Klient();
                klient.setKlientId(rs.getInt("klient_id"));
                klient.setNazwisko(rs.getString("Nazwisko"));
                klient.setImie(rs.getString("Imie"));
                klient.setPesel(rs.getString("Pesel"));
                klList.add(klient);
            }
            return klList;
        } catch (SQLException e) {
            System.out.println("Error"+e);
            e.printStackTrace();
            throw e;
        }
    }

    //Sprawdzanie czy klient istnieje w BD

    public boolean sprKlient(int idKlienta) {
        String SQL = "SELECT klient_id FROM klienci WHERE klient_id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)){
                pstmt.setInt(1, idKlienta);
                ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas sprawdzania danych klienta w BD");
            e.printStackTrace();
        }
        return false;
    }


    public void dodajHulajnoge(Hulajnogi hulajnoga) {
        String SQL = "INSERT INTO hulajnogi(hulajnoga_id, klasa, rok_nabycia) VALUES (?, ?, ?);";
        try {Connection conn = connect();
            prepStmt = conn.prepareStatement(SQL);
            prepStmt.setInt(1,hulajnoga.getHulajnogaId());
            prepStmt.setString(2, hulajnoga.getKlasa());
            prepStmt.setInt(3, hulajnoga.getRokNabycia());;
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem hulajnogi do BD");
            e.printStackTrace();
        }
    }

    public static void usunHulajnoge(int hulaId) {
        String SQL = "DELETE FROM hulajnogi WHERE hulajnoga_id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, hulaId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z usunieciem hulajnogi z BD");
            e.printStackTrace();
        }
    }

    public static ObservableList<Hulajnogi> wyswietlHulajnogi() throws SQLException {
        String SQL = "SELECT * FROM hulajnogi;";
        try {Connection conn = connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            ObservableList<Hulajnogi> hulaList = getHulajnogaObjects(rs);
            return hulaList;
        } catch (SQLException e) {
            System.out.println("Problem z dostaniem danych hulajnog z BD" + e);
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<Hulajnogi> getHulajnogaObjects(ResultSet rs) throws SQLException {
        try {
            ObservableList<Hulajnogi> hulaList = FXCollections.observableArrayList();
            while (rs.next()){
                Hulajnogi hulajnoga = new Hulajnogi();
                hulajnoga.setHulajnogaId(rs.getInt("hulajnoga_id"));
                hulajnoga.setKlasa(rs.getString("klasa"));
                hulajnoga.setRokNabycia(rs.getInt("rok_nabycia"));
                hulaList.add(hulajnoga);
            }
            return hulaList;
        } catch (SQLException e) {
            System.out.println("Error"+e);
            e.printStackTrace();
            throw e;
        }
    }

    //Sprawdzanie czy hulajnoga istnieje w BD
    public boolean sprHulajnoga(String num) {
        try {Connection conn = connect();
            prepStmt = conn.prepareStatement("SELECT nr_rejestr FROM egzemplarze WHERE (nr_rejestr='" + num + "')");
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                rs.close();
                prepStmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas szukania hulajnogi o danej rejestracji w BD!");
            e.printStackTrace();
        }
        return false;
    }

    //Sprawdzanie czy hulajnoga istnieje w BD
    public boolean sprHulajnogaId(int id) {
        try {Connection conn = connect();
            prepStmt = conn.prepareStatement("SELECT hulajnoga_id FROM egzemplarze WHERE (hulajnoga_id='" + id + "')");
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                rs.close();
                prepStmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas szukania hulajnogi o danym id w BD!");
            e.printStackTrace();
        }
        return false;
    }

    public void dodajEgzemplarz(Egzemplarze egz) {
        String SQL = "INSERT INTO egzemplarze(nr_rejestr, hulajnoga_id) VALUES (?, ?);";
        try {Connection conn = connect();
            prepStmt = conn.prepareStatement(SQL);
            prepStmt.setString(1, egz.getNr());
            prepStmt.setInt(2, egz.getHulajnogaId());;
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem egzemplarza do BD");
            e.printStackTrace();
        }
    }

    public static void usunEgzemplarz(String nr) {
        String SQL = "DELETE FROM egzemplarze WHERE nr_rejestr = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, nr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z usunięciem egzemplarza z BD");
            e.printStackTrace();
        }
    }

    public static ObservableList<Egzemplarze> wyswietlEgzemplarze() throws SQLException {
        String SQL = "SELECT * FROM egzemplarze;";
        try {Connection conn = connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            ObservableList<Egzemplarze> egzList = getEgzemplarzObjects(rs);
            return egzList;
        } catch (SQLException e) {
            System.out.println("Problem z dostaniem danych egzemplarzy z BD" + e);
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<Egzemplarze> getEgzemplarzObjects(ResultSet rs) throws SQLException {
        try {
            ObservableList<Egzemplarze> egzList = FXCollections.observableArrayList();
            while (rs.next()){
                Egzemplarze egz = new Egzemplarze();
                egz.setNr(rs.getString("nr_rejestr"));
                egz.setHulajnogaId(rs.getInt("hulajnoga_id"));
                egzList.add(egz);
            }
            return egzList;
        } catch (SQLException e) {
            System.out.println("Error"+e);
            e.printStackTrace();
            throw e;
        }
    }


    public void dodajWypozyczenie(ListaWypozyczen wypozyczenie) {
        String SQL = "INSERT INTO wypozyczenia(wypozyczenie_id, klient_id, nr_rejestr, godzina_od, godzina_do, usluga_nr) VALUES (?, ?, ?, ?, ?, ?);";
        try { Connection conn = connect();
            prepStmt = conn.prepareStatement(SQL);
            prepStmt.setInt(1, wypozyczenie.getWypozyczenieId());
            prepStmt.setInt(2, wypozyczenie.getKlientId());
            prepStmt.setString(3, wypozyczenie.getNrRejestr());
            prepStmt.setInt(4, wypozyczenie.getGodzinaOd());
            prepStmt.setInt(5, wypozyczenie.getGodzinaDo());
            prepStmt.setInt(6, wypozyczenie.getNrUslugi());
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem wypozyczenia do BD");
            e.printStackTrace();
        }
    }

    public static void usunWypozyczenie(int wypoId) {
        String SQL = "DELETE FROM wypozyczenia WHERE wypozyczenie_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, wypoId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z usunieciem wypozyczenia z BD");
            e.printStackTrace();
        }
    }

    public static ObservableList<ListaWypozyczen> wyswietlWypozyczenia() throws SQLException {
        String SQL = "SELECT * FROM wypozyczenia;";
        try {Connection conn = connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            ObservableList<ListaWypozyczen> wypList = getWypozyczenieObjects(rs);
            return wypList;
        } catch (SQLException e) {
            System.out.println("Problem z dostaniem danych wypozyczenia z BD" + e);
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<ListaWypozyczen> getWypozyczenieObjects(ResultSet rs) throws SQLException {
        try {
            ObservableList<ListaWypozyczen> wypList = FXCollections.observableArrayList();
            while (rs.next()){
                ListaWypozyczen wypozyczenie = new ListaWypozyczen();
                wypozyczenie.setWypozyczenieId(rs.getInt("wypozyczenie_id"));
                wypozyczenie.setKlientId(rs.getInt("klient_id"));
                wypozyczenie.setNrRejestr(rs.getString("nr_rejestr"));
                wypozyczenie.setGodzinaOd(rs.getInt("godzina_od"));
                wypozyczenie.setGodzinaDo(rs.getInt("godzina_do"));
                wypozyczenie.setNrUslugi(rs.getInt("usluga_nr"));
                wypList.add(wypozyczenie);
            }
            return wypList;
        } catch (SQLException e) {
            System.out.println("Error"+e);
            e.printStackTrace();
            throw e;
        }
    }

    //Sprawdzanie czy wypozyczenie istnieje w BD
    public static boolean sprWypozyczenie(String num) {
        try {Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement("SELECT wypozyczenie_id FROM wypozyczenia WHERE (wypozyczenie_id='" + num + "')");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas szukania wypozyczenia w BD!");
            e.printStackTrace();
        }
        return false;
    }



    public static int wystawRachunek(int wypozyczenieID) {
        String SQL = "SELECT * FROM  oblicz(?)";
        int sum = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, wypozyczenieID);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Suma kosztów za wypożyczenie pod nr " + wypozyczenieID + " wynosi " + sum + " zł.");
        return sum;
    }

    public void dodajUzytkownika(ListaUzytkownikow uzytkownik) {
        String SQL = "INSERT INTO logowanie(uzytkownik_id, login, haslo) VALUES (?, ?, ?);";
        try {Connection conn = connect();
            prepStmt = conn.prepareStatement(SQL);
            prepStmt.setInt(1, uzytkownik.getUzytkownikId());
            prepStmt.setString(2, uzytkownik.getLogin());
            prepStmt.setString(3, uzytkownik.getHaslo());

            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem użytkownika do BD");
            e.printStackTrace();
        }
    }

    public static void usunUzytkownika(String login) {
        String SQL = "DELETE FROM logowanie WHERE login = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, login);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z usunieciem użytkownika z BD");
            e.printStackTrace();
        }
    }

    public static boolean sprUzytkownik(String login, String haslo) {
        String SQL = "SELECT login, haslo FROM logowanie WHERE login = ? AND haslo = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)){
            pstmt.setString(1, login);
            pstmt.setString(2, haslo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas sprawdzania danych użytkownika w BD");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sprLogin(String login) {
        String SQL = "SELECT login, haslo FROM logowanie WHERE login = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)){
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas sprawdzania loginu użytkownika w BD");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sprLog(String login) {
        String SQL = "SELECT login FROM logowanie WHERE login = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)){
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas sprawdzania loginu użytkownika w BD");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sprId(int id) {
        String SQL = "SELECT uzytkownik_id FROM logowanie WHERE uzytkownik_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas sprawdzania id użytkownika w BD");
            e.printStackTrace();
        }
        return false;
    }

    public void dodajKontakt(Kontakt kon) {
        String SQL = "INSERT INTO telefony(nr_tel, klient_id) VALUES (?, ?);";
        try {Connection conn = connect();
            prepStmt = conn.prepareStatement(SQL);
            prepStmt.setString(1, kon.getTel());
            prepStmt.setInt(2, kon.getKlientId());
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem kontaktu do klienta");
            e.printStackTrace();
        }
    }

    public static void usunKontakt(String nr) {
        String SQL = "DELETE FROM telefony WHERE nr_tel = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, nr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem z usunieciem kontaktu do klienta z BD");
            e.printStackTrace();
        }
    }

    public static ObservableList<Kontakt> wyswietlKontakty() throws SQLException {
        String SQL = "SELECT * FROM telefony;";
        try {Connection conn = connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            ObservableList<Kontakt> konList = getKontaktObjects(rs);
            return konList;
        } catch (SQLException e) {
            System.out.println("Problem z dostaniem danych klientow z BD" + e);
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<Kontakt> getKontaktObjects(ResultSet rs) throws SQLException {
        try {
            ObservableList<Kontakt> konList = FXCollections.observableArrayList();
            while (rs.next()){
                Kontakt kon = new Kontakt();
                kon.setTel(rs.getString("nr_tel"));
                kon.setKlientId(rs.getInt("klient_id"));
                konList.add(kon);
            }
            return konList;
        } catch (SQLException e) {
            System.out.println("Error"+e);
            e.printStackTrace();
            throw e;
        }
    }

    public boolean sprTel(String nr) {
        String SQL = "SELECT nr_tel FROM telefony WHERE nr_tel = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)){
            pstmt.setString(1, nr);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas sprawdzania danego telefonu w BD");
            e.printStackTrace();
        }
        return false;
    }
}

