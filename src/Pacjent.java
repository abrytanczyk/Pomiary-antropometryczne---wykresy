import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;  //- jakas biblioteka do dat
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.text.ParseException;

public class Pacjent {

    //ATRYBUTY
    private String nazwisko;
    private String imie;
    private String pesel;
    private String adresUlica;
    private String adresMiasto;
    private int plec;           //1 = baba; 0 = facet (hehe); w dzisiejszych czasach musi byc to intem, a nie booleanem :c

    private String dataUrodzenia;
    private String okresRozwojowy;

    private PomiaryDziecko pomiaryDziecko;
    private PomiaryNiemowle pomiaryNiemowle;
    //private Pomiary pomiary;

    //KONSTRUKTOR

    public Pacjent(String nazwisko, String imie, String pesel, int plec) {
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.pesel = pesel;
        this.plec = plec;

        this.dataUrodzenia = makeDataUrodzenia();
        this.okresRozwojowy = makeOkresRozwojowy();
    }


    public Pacjent(String nazwisko, String imie, String pesel, int plec, String adresUlica, String adresMiasto) {
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.pesel = pesel;
        this.plec = plec;
        this.adresUlica = adresUlica;
        this.adresMiasto = adresMiasto;

        this.dataUrodzenia = makeDataUrodzenia();
        this.okresRozwojowy = makeOkresRozwojowy();
    }

    public Pacjent(String daneFile){

        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(daneFile));
            int i=0;
            while ((line = br.readLine()) != null) {

                String[] linia = line.split(",");

                this.nazwisko = linia[0];
                this.imie = linia[1];
                this.pesel = linia[2];
                this.plec = Integer.parseInt(linia[3]);
                if(linia.length > 4 && linia[4] != null && linia[5] !=null){
                    this.adresUlica = linia[4];
                    this.adresMiasto = linia[5];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.dataUrodzenia = makeDataUrodzenia();
        this.okresRozwojowy = makeOkresRozwojowy();
    }

    //GETTERY
    public String getNazwisko() {
        return nazwisko;
    }
    public String getImie() {
        return imie;
    }
    public String getPesel() {
        return pesel;
    }

    public String getAdresUlica() {
        return adresUlica;
    }

    public String getAdresMiasto() {
        return adresMiasto;
    }

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }
    public String getOkresRozwojowy() {
        return okresRozwojowy;
    }


    public PomiaryDziecko getPomiaryDziecko() { return pomiaryDziecko; }
    public void setPomiaryDziecko(PomiaryDziecko pomiaryDziecko) {
        this.pomiaryDziecko = pomiaryDziecko;
    }

    public PomiaryNiemowle getPomiaryNiemowle() { return pomiaryNiemowle; }
    public void setPomiaryNiemowle(PomiaryNiemowle pomiaryNiemowle) { this.pomiaryNiemowle = pomiaryNiemowle; }

    public int getPlec() {
        return plec;
    }

    public void setPlec(int plec) {
        this.plec = plec;
    }

    //METODY
    public String makeDataUrodzenia(){
        dataUrodzenia = pesel.substring(0, 6);
        int miesiac = Integer.parseInt(pesel.substring(2, 4));
        miesiac -= 20;
        if (miesiac < 10) {
            dataUrodzenia = dataUrodzenia.substring(0, 2) + "0" + miesiac + dataUrodzenia.substring(4, 6);
        }
        else {
            dataUrodzenia = dataUrodzenia.substring(0, 2) + miesiac + dataUrodzenia.substring(4, 6);
        }

        Date data = new Date();
        String pattern = "yyMMdd";
        String pattern1 = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
        try {
            data = simpleDateFormat.parse(dataUrodzenia);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dataUrodzenia = simpleDateFormat1.format(data);
        return dataUrodzenia;
    }

    public double [] obliczWiek() {
        LocalDate data = LocalDate.now();
        dataUrodzenia = makeDataUrodzenia(); //tak na wszelki wypadek, gdyby ktos nie zrobil najpierw makeDataUrodzenia
        String[] dataUrodzeniaCzesci = dataUrodzenia.split("-");
        int dzienUrodzenia = Integer.parseInt(dataUrodzeniaCzesci[0]);
        int miesiacUrodzenia = Integer.parseInt(dataUrodzeniaCzesci[1]);
        int rokUrodzenia = Integer.parseInt(dataUrodzeniaCzesci[2]);
        LocalDate dataUrodzenia = LocalDate.of(rokUrodzenia, miesiacUrodzenia, dzienUrodzenia);
        Period wiek = Period.between(dataUrodzenia, data);

        double [] wiekPacjenta = {wiek.getYears(), wiek.getMonths()};
        return wiekPacjenta;
    }

    public String makeOkresRozwojowy(){
        double [] wiekPacjenta = obliczWiek(); // = {lata, miesiace}
        if (wiekPacjenta[0] < 1 && wiekPacjenta[1] <= 2){
            return "noworodkowy";
        }
        else if (wiekPacjenta[0] < 1 || (wiekPacjenta[0] == 1 && wiekPacjenta[1] == 0)){
            return "niemowlęcy";
        }
        else if ((wiekPacjenta[0] >= 1 && wiekPacjenta[0] < 2) || (wiekPacjenta[0] == 2 && wiekPacjenta[1] == 0)){
            return "poniemowlęcy";
        }
        else if (wiekPacjenta[0] < 13 || (wiekPacjenta[0] == 13 && wiekPacjenta[1] == 0)){
            return  "dzieciństwa";
        }
        else {
            return "dojrzewania";
        }
    }

    //pomocnicze do rozróżnienia plików z danymi dla dzieci <=24 miesiące a starszymi
    public String typDanych(){
        if (okresRozwojowy == "dzieciństwa" || okresRozwojowy == "dojrzewania"){
            return "";
        }
        else {
            return "_wczesny";
        }
    }

    @Override
    public String toString() {
        return nazwisko + ' ' + imie + " [" + pesel + "]";
    }
}