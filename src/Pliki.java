import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Pliki {

    public void utworzFolderPacjent(String pesel, String nazwisko, String imie){
        String pathName = "./data/" + nazwisko + " " + imie + " [" + pesel + "]" ;
        try {
            Files.createDirectory(Paths.get(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.createDirectory(Paths.get(pathName + "/personal_data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void utworzFolderPlikiWizyta(String dirPacjent, String notatki, String pomiary){
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dataDzisiaj = simpleDateFormat.format(new Date());
        String pathName = "./data/" + dirPacjent + "/" + dataDzisiaj;
        try {
            Files.createDirectory(Paths.get(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter zapis = null;
        try {
            zapis = new PrintWriter(pathName + "/notatki.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        zapis.println(notatki);
        zapis.close();

        PrintWriter zapis1 = null;
        try {
            zapis1 = new PrintWriter(pathName + "/pomiary.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        zapis1.println(pomiary);
        zapis1.close();
    }

    public void utworzPlikDane(String nazwisko, String imie, String pesel, int plec, String adresUlica, String adresMiasto){
        String Pathname = "./data/" + nazwisko + " " + imie + " [" + pesel + "]";
        String dane = nazwisko + "," + imie + "," + pesel + "," + plec + "," + adresUlica + "," +  adresMiasto;
        PrintWriter zapis = null;
        try {
            zapis = new PrintWriter(Pathname + "/dane.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        zapis.println(dane);
        zapis.close();
    }

    public void dodajPomiary(String dataPath, String wartosc, double wiekPacjenta){

        String wiek = String.valueOf(wiekPacjenta);
        String linia = wiek +","+ wartosc;

        FileWriter fw = null;

        try {
            fw = new FileWriter(dataPath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(linia);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void usunFolder(String toDeletePath) {

        File file = new File(toDeletePath);
        usunFolderRekurencja(file);
        System.out.println("Files deleted........");
    }
    static void usunFolderRekurencja(File file){
        for (File subFile : file.listFiles()) {
            if(subFile.isDirectory()) {
                usunFolderRekurencja(subFile);
            } else {
                subFile.delete();
            }
        }
        file.delete();
    }

    public void generujSiatke(String dataFile, String destFile, String okresRozwojowy, int plec, int typ){

        //TODO zmienic rozmiary siatek dla poszczegolnych wykresow; co z legendą?:>

        //nd -niemowle/dziecko; 24-siatka dla niemowlęcia/poniem.; 18-dla reszty   (25 lub 17 progów)    !!!!!!!!!poprawka dla malych dzieci 24
        //id - typ wykresu:     11 - wzrost k,      10 - wzrost m
        //                      21 - waga k,        20 - waga m
        //                      31 - bmi k,         30 - bmi m
        //                      41 - cisnienie k,   40 - cisnienie m
        //                      51 - obwod glowy k, 50 - obwod glowy m
        //                      61 - obwod klatki k,60 - obwod klatki m

        //ROZNY ROZMIAR i podziałka, robocze - deklaracja zmiennych
        int szerokoscWykresu = 1200;
        int wysokoscWykresu = 800;
        int ylimGora = 100;
        int ylimDol = 0;
        int xlimDol = 0;
        int xlimGora = 100;

        String csvFile = "";
        String csvFile1 = "";
        String tytulOY = "";
        String tytulWykresu = "";

        //USTALANIE n/d
        int nd = 0;
        if (okresRozwojowy.equals("dzieciństwa") || okresRozwojowy.equals("dojrzewania")) {
            nd = 18;                  //bo od 2 do 18
            xlimDol = 2;
            xlimGora = 18;
        } else {
            nd = 24;                 //bo od 0 do 24
            xlimGora = 24;
            xlimDol = 0;
        }

        //USTALANIE typu (id)
        int id = 10 * typ + plec;

        if (id == 11){
            csvFile = "./csv/wzrost_k_" + Integer.toString(nd) + ".csv";
            tytulOY = "Wzrost";
            tytulWykresu = "Wzrost dziewcząt";
            if (nd == 24) {
                ylimDol = 44;
                ylimGora = 94;
            } else {
                ylimDol = 70;
                ylimGora = 180;
            }
        }
        else if (id == 10){
            csvFile = "./csv/wzrost_m_" + Integer.toString(nd) + ".csv";
            tytulOY = "Wzrost";
            tytulWykresu = "Wzrost chłopców";
            if (nd == 24) {
                ylimDol = 44;
                ylimGora = 100;
            }
            else {
                ylimDol = 70;
                ylimGora = 200;
            }
        }
        else if (id == 21){
            csvFile = "./csv/waga_k_" + Integer.toString(nd) +".csv";
            tytulOY = "Masa ciała";
            tytulWykresu = "Masa ciała dziewcząt";
            if (nd == 24) {
                ylimDol = 1;
                ylimGora = 16;
            } else {
                ylimDol = 0;
                ylimGora = 80;
            }
        }
        else if (id == 20){
            csvFile = "./csv/waga_m_" + Integer.toString(nd) +".csv";
            tytulOY = "Masa ciała";
            tytulWykresu = "Masa ciała chłopców";
            if (nd == 24) {
                ylimDol = 0;
                ylimGora = 16;
            } else {
                ylimDol = 0;
                ylimGora = 95;
            }
        }
        else if (id == 30){
            csvFile = "./csv/bmi_m_" + Integer.toString(nd) +".csv";
            tytulOY = "BMI";
            tytulWykresu = "BMI chłopców";
            ylimDol = 11;
            if (nd == 18) {
                ylimGora = 30;
            }
            else {
                ylimGora = 20;
            }
        }
        else if (id == 31){
            csvFile = "./csv/bmi_k_" + Integer.toString(nd) +".csv";
            tytulOY = "BMI";
            tytulWykresu = "BMI dziewcząt";
            ylimDol = 11;
            if (nd == 18) {
                ylimGora = 28;
            }
            else {
                ylimGora = 20;
            }
        }
        else if (id == 41){
            csvFile = "./csv/cisnienieskurczowe_k_" + Integer.toString(nd) +".csv";
            csvFile1 = "./csv/cisnienerozkurczowe_k_" + Integer.toString(nd) +".csv";
            tytulOY = "Ciśnienie";
            tytulWykresu = "Ciśnienie dziewcząt";
            ylimDol = 50;
            ylimGora = 155;
        }
        else if (id == 40){
            csvFile = "./csv/cisnienieskurczowe_m_" + Integer.toString(nd) +".csv";
            csvFile1 = "./csv/cisnienerozkurczowe_m_" + Integer.toString(nd) +".csv";
            tytulOY = "Ciśnienie";
            tytulWykresu = "Ciśnienie chłopców";
            ylimDol = 50;
            ylimGora = 155;
        }
        else if (id == 51){
            csvFile = "./csv/obwodglowy_k_" + Integer.toString(nd) +".csv";
            tytulOY = "Obwód głowy";
            tytulWykresu = "Obwód głowy dziewcząt";
            if (nd == 24) {
                ylimDol = 31;
                ylimGora = 52;
            } else {
                ylimDol = 43;
                ylimGora = 58;
            }
        }
        else if (id == 50) {
            csvFile = "./csv/obwodglowy_m_" + Integer.toString(nd) + ".csv";
            tytulOY = "Obwód głowy";
            tytulWykresu = "Obwód głowy chłopców";
            if (nd == 24) {
                ylimDol = 31;
                ylimGora = 52;
            } else {
                ylimDol = 43;
                ylimGora = 60;
            }
        }
        else if (id == 61) {
            csvFile = "./csv/obwodklatki_k_" + Integer.toString(nd) + ".csv";
            tytulOY = "Obwód klatki piersiowej";
            tytulWykresu = "Obwód klatki piersiowej dziewcząt";
            if (nd == 24) {
                ylimDol = 29;
                ylimGora = 54;
            }
            /*   sekcja umarta, opcjonalnie można kiedyś dodać
            else {
                ylimDol = 40;
                ylimGora = 60;
            }
            */
        }
        else if (id == 60) {
            csvFile = "./csv/obwodklatki_m_" + Integer.toString(nd) + ".csv";
            tytulOY = "Obwód klatki piersiowej";
            tytulWykresu = "Obwód klatki piersiowej chłopców";
            if (nd == 24) {
                ylimDol = 30;
                ylimGora = 55;
            }
            /*   sekcja umarta, opcjonalnie można kiedyś dodać
            else {
                ylimDol = 40;
                ylimGora = 60;
            }
            */
        }

////////////////////////
        BufferedReader br = null;
        String line = "";

        if (nd == 18){
            nd = 17;
        } else {
            nd = 25;
        }
        nd++;
        double[] x = new double[nd];
        double[] y1 = new double[nd];
        double[] y2 = new double[nd];
        double[] y3 = new double[nd];
        double[] y4 = new double[nd];
        double[] y5 = new double[nd];
        double[] y6 = new double[nd];
        double[] y7 = new double[nd];

        if(id == 40 || id == 41){               //osobne traktowanie dla cisnienia

            try {
                br = new BufferedReader(new FileReader(csvFile));
                int i = 0;

                String[] naglowek = br.readLine().split(",");

                while ((line = br.readLine()) != null) {

                    String[] linia = line.split(",");

                    x[i] = Double.parseDouble(linia[0]);
                    y1[i] = Double.parseDouble(linia[1]);
                    y2[i] = Double.parseDouble(linia[2]);
                    y3[i] = Double.parseDouble(linia[3]);

                    if(i==0) {
                        x[i] += 0.000001;
                    }

                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new BrakDanychException();

            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                br = new BufferedReader(new FileReader(csvFile1));
                int i = 0;

                String[] naglowek = br.readLine().split(",");

                while ((line = br.readLine()) != null) {

                    String[] linia = line.split(",");

                    y7[i] = Double.parseDouble(linia[1]);
                    y6[i] = Double.parseDouble(linia[2]);
                    y5[i] = Double.parseDouble(linia[3]);

                    i++;
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
        } else {            //normalni smiertelnicy - wszytskie wykresy procz cisnienia

            try {
                br = new BufferedReader(new FileReader(csvFile));
                int i = 0;

                //nagłowek z csv - mamy ktore centyle - na razie nigdzie nie użyty, czeka na lepsze czasy
                String[] naglowek = br.readLine().split(",");

                while ((line = br.readLine()) != null) {

                    String[] linia = line.split(",");

                    x[i] = Double.parseDouble(linia[0]);
                    y1[i] = Double.parseDouble(linia[1]);
                    y2[i] = Double.parseDouble(linia[2]);
                    y3[i] = Double.parseDouble(linia[3]);
                    y4[i] = Double.parseDouble(linia[4]);
                    y5[i] = Double.parseDouble(linia[5]);
                    y6[i] = Double.parseDouble(linia[6]);
                    y7[i] = Double.parseDouble(linia[7]);

                    if(i==0) {
                        x[i] += 0.000001;
                    }

                    i++;
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
////////////////////////
        ArrayList<Double> yList = new ArrayList<Double>();
        ArrayList<Double> xList = new ArrayList<Double>();
        ArrayList<Double> y1List = new ArrayList<Double>(); //dla cisnienia

        try {
            br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {
                String[] linia = line.split(",");
                xList.add(Double.parseDouble(linia[0]));
                yList.add(Double.parseDouble(linia[1]));
                if(id == 40 || id == 41) { //dla cisnienia
                    y1List.add(Double.parseDouble(linia[2]));
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
        double [] y = new double[yList.size()];
        double [] x1 = new double[xList.size()];
        double [] y_1 = new double[y1List.size()];
        for (int i=0; i<yList.size(); i++){
            x1[i] = xList.get(i);
            y[i] = yList.get(i);
            if(id == 40 || id == 41) { //dla cisnienia
                y_1[i] = y1List.get(i);
            }
        }
//////////////////////////////
        MatlabChart fig = new MatlabChart();
        fig.plot(x, y1, ":k", 1.0f, "centyl 3", true);
        fig.plot(x, y2, ".r", 1.0f, "centyl 10", true);
        fig.plot(x, y3, "-b", 1.0f, "centyl 25", true);
        fig.plot(x, y4, "-g", 2.0f, "centyl 50", true);
        fig.plot(x, y5, "-b", 1.0f, "centyl 75", true);
        fig.plot(x, y6, ".r", 1.0f, "centyl 90", true);
        fig.plot(x, y7, ":k", 1.0f, "centyl 97",true);

        fig.plot(x1, y, "-r", 3.0f, "dane", false);
        if(id == 40 || id == 41) { //dla cisnienia
            fig.plot(x1, y_1, "-r", 3.0f, "dane1", false);
        }

        ////////////////
        fig.RenderPlot();
        fig.title(tytulWykresu);
        fig.xlim(xlimDol, xlimGora);
        fig.ylim(ylimDol, ylimGora);
        if (okresRozwojowy.equals("dzieciństwa") || okresRozwojowy.equals("dojrzewania")) {
            fig.xlabel("Wiek [lata]");
        }
        else {
            fig.xlabel("Wiek [miesiące]");
        }
        fig.ylabel(tytulOY);
        fig.grid("on","on");
        fig.legend("southeast");
        fig.font("Helvetica",15);
        fig.saveas(destFile,szerokoscWykresu,wysokoscWykresu);
    }
}
