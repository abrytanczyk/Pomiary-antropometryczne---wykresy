import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PomiaryDziecko extends Pomiary{

    //ATRYBUTY
    private double cisnienie1;
    private double cisnienie2;

    //KONSTRUKTORY
    public PomiaryDziecko(double wzrost, double waga, String notatki, double cisnienie1, double cisnienie2) {
        super(wzrost, waga, notatki);
        this.cisnienie1 = cisnienie1;
        this.cisnienie2 = cisnienie2;
    }

    public PomiaryDziecko(String dane){  //konstruktor z pliku
        super(dane);
        String pomiaryPath = dane + "/pomiary.txt";
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(pomiaryPath));
            int i=0;
            while ((line = br.readLine()) != null) {

                String[] linia = line.split(",");
                this.cisnienie1 = Double.parseDouble(linia[2]);
                this.cisnienie2 = Double.parseDouble(linia[3]);
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
    }

    //GETTERY
    public double getCisnienie1() { return cisnienie1; }
    public double getCisnienie2() { return cisnienie2; }
    public String getCisnienie() {
        return cisnienie1 + "/" + cisnienie2;
    }

    //zapis do plikow
    public void zapiszDoPliku(Pacjent pacjent) {
        Pliki pliki = new Pliki();
        double [] wiek = pacjent.obliczWiek();
        double wiekPacjenta = wiek[0] + wiek[1]/12;
        String dirPacjent = pacjent.toString();
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/waga.txt", String.valueOf(getWaga()), wiekPacjenta);
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/wzrost.txt", String.valueOf(getWzrost()), wiekPacjenta);
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/cisnienie.txt", String.valueOf(getCisnienie1()) + "," + String.valueOf(getCisnienie2()), wiekPacjenta);
        pliki.dodajPomiary("./data/" + dirPacjent + "/personal_data/bmi.txt", String.valueOf(getBmi()), wiekPacjenta);
    }
}
