DROP TABLE logowanie CASCADE;
CREATE TABLE logowanie (
				uzytkownik_id SERIAL NOT NULL,
                login VARCHAR NOT NULL,
                haslo VARCHAR NOT NULL
);

DROP TABLE hulajnogi CASCADE;
CREATE TABLE hulajnogi (
                hulajnoga_id SERIAL NOT NULL,
                klasa TEXT NOT NULL,
                rok_nabycia INTEGER NOT NULL
);

DROP TABLE cennik CASCADE;
CREATE TABLE cennik (
                klasa TEXT NOT NULL,
                cena_godzina INTEGER NOT NULL
);

DROP TABLE egzemplarze CASCADE;
CREATE TABLE egzemplarze (
                nr_rejestr VARCHAR NOT NULL,
                hulajnoga_id INTEGER NOT NULL
);

DROP TABLE uslugi CASCADE;
CREATE TABLE uslugi (
                usluga_nr SERIAL NOT NULL,
                nazwa TEXT NOT NULL,
                opis TEXT NOT NULL
);

DROP TABLE klienci CASCADE;
CREATE TABLE klienci (
                klient_id SERIAL NOT NULL,
                nazwisko TEXT NOT NULL,
                imie TEXT,
                pesel VARCHAR(11) NOT NULL
);

DROP TABLE telefony CASCADE;
CREATE TABLE telefony (
                nr_tel VARCHAR NOT NULL,
                klient_id INTEGER NOT NULL
);

DROP TABLE wypozyczenia CASCADE;
CREATE TABLE wypozyczenia (
				wypozyczenie_id SERIAL NOT NULL,
				klient_id INTEGER NOT NULL,
				nr_rejestr VARCHAR(9) NOT NULL,
				godzina_od INTEGER NOT NULL,
				godzina_do INTEGER NOT NULL,
				usluga_nr INTEGER
);


ALTER TABLE logowanie ADD PRIMARY KEY (uzytkownik_id);
ALTER TABLE logowanie ADD UNIQUE (login);

ALTER TABLE hulajnogi ADD PRIMARY KEY (hulajnoga_id);

ALTER TABLE cennik ADD PRIMARY KEY (klasa);

ALTER TABLE egzemplarze ADD PRIMARY KEY (nr_rejestr);

ALTER TABLE uslugi ADD PRIMARY KEY (usluga_nr);

ALTER TABLE klienci ADD PRIMARY KEY (klient_id);
ALTER TABLE klienci ADD UNIQUE (pesel);

ALTER TABLE telefony ADD PRIMARY KEY (nr_tel);

ALTER TABLE wypozyczenia ADD PRIMARY KEY (wypozyczenie_id);


ALTER TABLE hulajnogi ADD FOREIGN KEY (klasa) REFERENCES
cennik (klasa)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE egzemplarze ADD FOREIGN KEY (hulajnoga_id) REFERENCES
hulajnogi (hulajnoga_id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE wypozyczenia ADD FOREIGN KEY (klient_id) REFERENCES
klienci (klient_id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE wypozyczenia ADD FOREIGN KEY (nr_rejestr) REFERENCES
egzemplarze (nr_rejestr)
ON UPDATE CASCADE;

ALTER TABLE wypozyczenia ADD FOREIGN KEY (usluga_nr) REFERENCES
uslugi (usluga_nr)
ON UPDATE CASCADE;


ALTER TABLE klienci ADD CHECK (pesel ~ '^[0-9]*$');
ALTER TABLE klienci ADD CHECK (length(pesel) IN (11));

ALTER TABLE hulajnogi ADD CHECK (rok_nabycia > 1999 AND rok_nabycia < 2021);

ALTER TABLE wypozyczenia ADD CHECK (godzina_od < godzina_do);


CREATE OR REPLACE FUNCTION opis_usluga(n TEXT) RETURNS TEXT AS '
DECLARE
	x RECORD;
BEGIN
	SELECT INTO x * FROM uslugi WHERE nazwa = n;
	RETURN x.opis;
END;
' LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION opis_klient(nr INTEGER) RETURNS RECORD AS '
DECLARE
	x RECORD;
BEGIN
	SELECT INTO x * FROM klienci AS k, telefony AS t
	WHERE k.klient_id = nr AND k.klient_id = t.klient_id;
	RETURN x;
END;
' LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION dodaj_klient(n TEXT, i TEXT, p VARCHAR(11))
RETURNS INTEGER AS '
BEGIN
	INSERT INTO klienci(nazwisko, imie, pesel) VALUES(n, i, p);
	RETURN 0;
END;
' LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION wypozycz(k INTEGER, nr_r VARCHAR(9), g_od VARCHAR(10), g_do VARCHAR(10), nr_u INTEGER) RETURNS INTEGER AS '
BEGIN
	INSERT INTO wypozyczenia(klient_id, nr_rejestr, godzina_od, godzina_do, usluga_nr) VALUES(k, nr_r, g_od, g_do, nr_u);
	RETURN 0;
END;
' LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION oblicz(I INTEGER)
RETURNS INTEGER AS '
DECLARE
	result INTEGER;
BEGIN
	SELECT ((w.godzina_do - w.godzina_od)*c.cena_godzina) INTO result FROM wypozyczenia w
	JOIN egzemplarze e ON w.nr_rejestr = e.nr_rejestr
	JOIN hulajnogi h ON h.hulajnoga_id = e.hulajnoga_id
	JOIN cennik c ON c.klasa = h.klasa
	WHERE w.wypozyczenie_id = I;
	RETURN result;
END;
' LANGUAGE 'plpgsql';


DROP VIEW wypozyczenia_rejestr CASCADE;
CREATE VIEW wypozyczenia_rejestr AS (
	SELECT k.nazwisko AS "Nazwisko", k.imie AS "Imie", h.klasa AS "Klasa", u.opis AS "Uslugi"
	FROM klienci AS k, wypozyczenia AS w, egzemplarze AS he, hulajnogi AS h, uslugi AS u
	WHERE k.klient_id = w.klient_id AND w.nr_rejestr = he.nr_rejestr AND he.hulajnoga_id = h.hulajnoga_id AND w.usluga_nr = u.usluga_nr
	ORDER BY k.nazwisko
);

DROP VIEW hulajnogi_dostepne CASCADE;
CREATE VIEW hulajnogi_dostepne AS(
	SELECT he.nr_rejestr AS "Numer Rejestracyjny", h.klasa AS "Klasa", h.rok_nabycia AS "Rok nabycia"
	FROM egzemplarze AS he, hulajnogi AS h
	WHERE he.hulajnoga_id = h.hulajnoga_id
	ORDER BY he.nr_rejestr
);

DROP VIEW nr_telefonow CASCADE;
CREATE VIEW nr_telefonow AS(
	SELECT k.nazwisko AS "Nazwisko", k.imie AS "Imie", t.nr_tel AS "Numer"
	FROM klienci AS k, telefony AS t
	WHERE k.klient_id = t.klient_id
	ORDER BY k.nazwisko
);

CREATE OR REPLACE FUNCTION spr_wiek() RETURNS TRIGGER AS '
BEGIN
	IF ((substring(NEW.pesel from 1 for 2) > ''08'') AND (substring(NEW.pesel from 1 for 2) < ''21'')) THEN
		RAISE EXCEPTION ''Wiek klienta: minumum 12 lat!'';
	END IF;
	RETURN NEW;
END;
' LANGUAGE 'plpgsql';

DROP TRIGGER wiek_trigger ON klienci CASCADE;
CREATE TRIGGER wiek_trigger BEFORE INSERT OR UPDATE ON klienci
FOR EACH ROW EXECUTE PROCEDURE spr_wiek();


CREATE OR REPLACE FUNCTION spr_rok() RETURNS TRIGGER AS '
BEGIN
	IF NEW.rok_nabycia < 2010 THEN
		RAISE EXCEPTION ''Tylko hulajnogi z rocznika 2010 lub nowsze!'';
	END IF;
	RETURN NEW;
END;
' LANGUAGE 'plpgsql';

DROP TRIGGER rok_trigger ON hulajnogi CASCADE;
CREATE TRIGGER rok_trigger BEFORE INSERT OR UPDATE ON hulajnogi
FOR EACH ROW EXECUTE PROCEDURE spr_rok();


INSERT INTO logowanie (login, haslo) VALUES ('admin', '123bu');

INSERT INTO cennik (klasa, cena_godzina) VALUES ('Elektryczna', 15);
INSERT INTO cennik (klasa, cena_godzina) VALUES ('Zwykla', 11);

INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Elektryczna', 2017);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Elektryczna', 2016);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Elektryczna', 2015);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Elektryczna', 2019);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Elektryczna', 2018);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Zwykla', 2010);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Zwykla', 2011);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Zwykla', 2013);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Zwykla', 2012);
INSERT INTO hulajnogi (klasa, rok_nabycia) VALUES ('Zwykla', 2014);

INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('AAA 12345', 1);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('BBB 12345', 2);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('CCC 12345', 3);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('DDD 12345', 4);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('EEE 12345', 5);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('FFF 12345', 6);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('GGG 12345', 7);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('HHH 12345', 8);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('III 12345', 9);
INSERT INTO egzemplarze (nr_rejestr, hulajnoga_id) VALUES ('JJJ 12345', 10);

INSERT INTO uslugi (nazwa, opis) VALUES ('Pakiet podstawowy', 'Kask, nawigacja GPS');
INSERT INTO uslugi (nazwa, opis) VALUES ('Pakiet rozszerzony', 'Kask, nawigacja GPS, ubezpieczenie');

INSERT INTO klienci (nazwisko, imie, pesel) VALUES ('Kowalski', 'Jan', '95073028496');
INSERT INTO klienci (nazwisko, imie, pesel) VALUES ('Nowak', 'Adam', '99090577434');
INSERT INTO klienci (nazwisko, imie, pesel) VALUES ('Bosak', 'Piotr', '96121225677');
INSERT INTO klienci (nazwisko, imie, pesel) VALUES ('Tylka', 'Izabela', '88101045669');
INSERT INTO klienci (nazwisko, imie, pesel) VALUES ('Kaczmarek', 'Anna', '00010234876');
INSERT INTO klienci (nazwisko, imie, pesel) VALUES ('Wysocka', 'Helena', '02112678345');

INSERT INTO telefony (nr_tel, klient_id) VALUES ('123456789', 1);
INSERT INTO telefony (nr_tel, klient_id) VALUES ('987654321', 2);
INSERT INTO telefony (nr_tel, klient_id) VALUES ('123789345', 3);
INSERT INTO telefony (nr_tel, klient_id) VALUES ('987123654', 4);
INSERT INTO telefony (nr_tel, klient_id) VALUES ('789123456', 5);
INSERT INTO telefony (nr_tel, klient_id) VALUES ('234678159', 6);

INSERT INTO wypozyczenia (klient_id, nr_rejestr, godzina_od, godzina_do, usluga_nr) VALUES (1, 'AAA 12345', '17', '18', 2);
INSERT INTO wypozyczenia (klient_id, nr_rejestr, godzina_od, godzina_do, usluga_nr) VALUES (3, 'CCC 12345', '8', '10', 1);