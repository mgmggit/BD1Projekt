package main.java.wypozyczalnia.java.fx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.wypozyczalnia.java.fx.model.Klient;
import main.java.wypozyczalnia.java.fx.model.Hulajnogi;
import main.java.wypozyczalnia.java.fx.model.ListaWypozyczen;
import javafx.collections.ObservableList;

public class WypozyczalniaHulajnog {

    private final String url = "jdbc:postgresql://localhost:5431/u8grzesiak";
    private final String user = "u8grzesiak";
    private final String password = "8grzesiak";

    private Connection conn;
    private Statement stat;
    private PreparedStatement prepStmt;
    private ResultSet rs;

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


    //Dodawanie klienta do tablicy

    public int dodajKlienta(Klient klient) {
        String SQL = "INSERT INTO klienci VALUES (?, ?, ?);";
        int id = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepStmt.setString(1, klient.getImie());
            prepStmt.setString(2, klient.getNazwisko());
            prepStmt.setString(3, klient.getPesel());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

//    public void dodajKlienta(Klient klient) {
//        try {
//            prepStmt = conn.prepareStatement("insert into klienci values (?, ?, ?);");
//            prepStmt.setString(1, klient.getImie());
//            prepStmt.setString(2, klient.getNazwisko());
//            prepStmt.setString(3, klient.getPesel());
//            prepStmt.execute();
//        } catch (SQLException e) {
//            System.out.println("Problem z dodaniem klienta do BD");
//            e.printStackTrace();
//        }
//    }

    //Dodawanie hulajnogi do tablicy

    public int dodajHulajnoge(Hulajnogi hulajnoga) {
        String SQL = "INSERT INTO hulajnogi VALUES (?, ?);";
        int id = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepStmt.setString(1, hulajnoga.getKlasa());
            prepStmt.setInt(2, hulajnoga.getRokNabycia());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    //Dodawanie wypozyczenia

    public int dodajWypozyczenie(ListaWypozyczen wypozyczenie) {
        String SQL = "INSERT INTO wypozyczenia VALUES (?, ?, ?, ?, ?);";
        int id = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepStmt.setInt(1, wypozyczenie.getKlientId());
            prepStmt.setString(2, wypozyczenie.getNrRejestr());
            prepStmt.setInt(3, wypozyczenie.getGodzinaOd());
            prepStmt.setInt(4, wypozyczenie.getGodzinaDo());
            prepStmt.setInt(5, wypozyczenie.getNrUslugi());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    //Ladowanie hulajnog do observable list

    public void loadListaHulajnogi(ObservableList<Hulajnogi> list) {
        String SQL = "SELECT * FROM hulajnogi";
        try {
            prepStmt = conn.prepareStatement(SQL);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                list.add(new Hulajnogi(
                        rs.getString("klasa"),
                        rs.getInt("rok nabycia")
                ));
            }
            prepStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Problem z dostaniem danych z BD");
            e.printStackTrace();
        }
    }

    //Ladowanie klientow do observable list

    public void loadListaKlienci(ObservableList<Klient> list) {
        String SQL = "SELECT * FROM klienci";
        try {
            prepStmt = conn.prepareStatement(SQL);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                list.add(new Klient(
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getString("pesel")
                ));
            }
            prepStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Problem z dostaniem danych z BD");
            e.printStackTrace();
        }
    }

    //Sprawdzanie czy klient istnieje w BD

    public boolean sprKlient(int idKlienta) {
        try {
            prepStmt = conn.prepareStatement("SELECT klient_id FROM klienci WHERE klient_id='" + idKlienta + "' )");
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                prepStmt.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas sprawdzania danych w BD");
            e.printStackTrace();
        }
        return false;
    }

    //Sprawdzanie czy hulajnoga istnieje w BD
    public boolean sprHulajnoga(String num) {
        try {
            prepStmt = conn.prepareStatement("SELECT nr_rejestr FROM egzemplarze WHERE (nr_rejestr='" + num + "')");
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                rs.close();
                prepStmt.close();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas szukania danych w BD!");
            e.printStackTrace();
        }
        return false;
    }

    //Hulajnoga informacje
    public String hulajnogaInfo(String num) {
        String nrRejestr;
        int hulajnogaId;
        try {
            prepStmt = conn.prepareStatement("SELECT nr_rejestr, hulajnoga_id FROM egzemplarze WHERE nr_rejestr='" + num + "'");
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                System.out.println("Znaleziono w bazie!");
                nrRejestr = rs.getString("nrRejestr");
                hulajnogaId = rs.getInt("hulajnogaId");
                prepStmt.close();
                return nrRejestr + " " + hulajnogaId;
            }
        } catch (SQLException e) {
            System.out.println("Problem podczas szukania danych w BD!");
            e.printStackTrace();
        }
        return "";
    }


    public void getKlienci() {

        String SQL = "SELECT * FROM klienci";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            wyswietlKlienci(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void wyswietlKlienci(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("klient_id") + "\t"
                    + rs.getString("nazwisko") + "\t"
                    + rs.getString("imie") + "\t"
                    + rs.getString("pesel"));

        }
    }

    public void getHulajnogi() {

        String SQL = "SELECT * FROM hulajnogi";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            wyswietlHulajnogi(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void wyswietlHulajnogi(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("hulajnoga_id") + "\t"
                    + rs.getString("klasa") + "\t"
                    + rs.getInt("rok_nabycia"));

        }
    }

    public void getWypozyczenia() {

        String SQL = "SELECT * FROM wypozyczenia";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            wyswietlWypozyczenia(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void wyswietlWypozyczenia(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("wypozyczenie_id") + "\t"
                    + rs.getString("klient_id") + "\t"
                    + rs.getString("nr_rejestr") + "\t"
                    + rs.getInt("godzina_od") + "\t"
                    + rs.getInt("godzina_do") + "\t"
                    + rs.getInt("usluga_nr"));

        }
    }

    public void getRachunek(int wypozyczenieID) {
        String SQL = "SELECT * FROM  oblicz(?)";
        int sum = 0;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, wypozyczenieID);
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Suma kosztów za wypożyczenie pod nr " + wypozyczenieID + " wynosi " + sum + " zł.");
    }


    //Ladowanie wypozyczen do observable list
    public void loadListaWypozyczen(ObservableList<ListaWypozyczen> list) {
        String SQL = "SELECT * FROM wypozyczenia";
        try {
            prepStmt = conn.prepareStatement(SQL);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                list.add(new ListaWypozyczen(
                                rs.getInt("klient_id"),
                                rs.getString("nr_rejestr"),
                                rs.getInt("godzina_od"),
                                rs.getInt("godzina_do"),
                                rs.getInt("usluga_nr")
                ));
            }
            prepStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Problem during getting data from database");
            e.printStackTrace();
        }
    }
}
