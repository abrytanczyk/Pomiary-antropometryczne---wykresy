import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PomiaryNiemowle extends Pomiary {      // chyba xd

    //ATRYBUTY
    private double obwodGlowy;
    private double obwodKlatki;

    //KONSKTUKTORY
    public PomiaryNiemowle(double wzrost, double waga, String notatki, double obwodGlowy, double obwodKlatki){
        super(wzrost, waga, notatki);
        this.obwodGlowy = obwodGlowy;
        this.obwodKlatki = obwodKlatki;
    }

    public PomiaryNiemowle(String dane) { //konstruktor z pliku
        super(dane);
        String pomiaryPath = dane + "/pomiary.txt";
        BufferedReader br = null;
        String line = "";
        Double obwodGlowy = null;
        Double obwodKlatki = null;

        try {
            br = new BufferedReader(new FileReader(pomiaryPath));
            int i=0;
            while ((line = br.readLine()) != null) {

                String[] linia = line.split(",");
                obwodGlowy = Double.parseDouble(linia[2]);
                obwodKlatki = Double.parseDouble(linia[3]);
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
        this.obwodGlowy = obwodGlowy;
        this.obwodKlatki = obwodKlatki;
    }

    public double getObwodGlowy() {
        return obwodGlowy;
    }
    public double getObwodKlatki() {
        return obwodKlatki;
    }

    //zapis do plikow
    public void zapiszDoPliku(Pacjent pacjent) {
        Pliki pliki = new Pliki();
        double [] wiek = pacjent.obliczWiek();
        double wiekPacjenta = 12 * wiek[0] + wiek[1];
        String dirPacjent = pacjent.toString();
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/obwodGlowy" + "_wczesny" + ".txt", String.valueOf(obwodGlowy), wiekPacjenta);
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/obwodKlatki" + "_wczesny" + ".txt", String.valueOf(obwodKlatki), wiekPacjenta);
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/waga" + "_wczesny" + ".txt", String.valueOf(getWaga()), wiekPacjenta);
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/wzrost" + "_wczesny" + ".txt", String.valueOf(getWzrost()), wiekPacjenta);
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/bmi" + "_wczesny" + ".txt", String.valueOf(getBmi()), wiekPacjenta);
    }
}
