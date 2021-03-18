import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pomiary {

    //ATRYBUTY
    private double wzrost;
    private double waga;
    private double bmi;
    private String notatki;

    //KONSTRUKTOR
    public Pomiary( double wzrost, double waga, String notatki) {
        this.wzrost = wzrost;
        this.waga = waga;
        this.notatki = notatki;
        this.bmi = makeBmi();
    }

    public Pomiary(String dane){  //konstruktor z pliku
        String notatkiPath = dane + "/notatki.txt";
        String pomiaryPath = dane + "/pomiary.txt";
        BufferedReader br = null;
        String line = "";
        String notatki = "";
        try {
            br = new BufferedReader(new FileReader(notatkiPath));
            int i=0;
            while ((line = br.readLine()) != null) {
                notatki += line + "\n";
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
        this.notatki = notatki;

        br = null;
        line = "";

        try {
            br = new BufferedReader(new FileReader(pomiaryPath));
            int i=0;
            while ((line = br.readLine()) != null) {

                String[] linia = line.split(",");

                this.wzrost = Double.parseDouble(linia[0]);
                this.waga = Double.parseDouble(linia[1]);

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
        this.bmi = makeBmi();

    }

    //GETTERY
    public double getWzrost() {
        return wzrost;
    }
    public double getWaga() {
        return waga;
    }
    public double getBmi() {
        return bmi;
    }
    public String getNotatki() {
        return notatki;
    }


    public double makeBmi(){
        double bmi = waga / ((wzrost/100)*(wzrost/100));
        return bmi;
    }

}
