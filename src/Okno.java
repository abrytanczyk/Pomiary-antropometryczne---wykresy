import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Okno {
    private JPanel main;
    private JComboBox listaPacjentow;
    private JButton wczytajPacjentaButton;
    private JTextField nazwiskoTF;
    private JTextField imieTF;
    private JTextField peselTF;
    private JRadioButton kobietaRadioButton;
    private JRadioButton mężczyznaRadioButton;
    private JButton nowyPacjentButton;
    private JTextField adresUlicaTF;
    private JTextField dataUrodzeniaTF;
    private JTextField okresRozwojowyTF;
    private JComboBox listaWizyt;
    private JButton wczytajWizyteButton;
    private JButton nowaWizytaButton;
    private JTextField wzrostTF;
    private JTextField wagaTF;
    private JTextField bmiTF;
    private JButton wzrostuButton;
    private JButton wagiButton;
    private JButton bmiButton;
    private JTextArea notatkiTA;
    private JTextField adresMiastoTF;
    private JTextField cisnienieTF;
    private JTextField obwodGlowyTF;
    private JTextField obwodKlatkiTF;
    private JButton ciśnieniaButton;
    private JButton obwGłowyButton;
    private JButton obwKlatkiButton;
    //private JPanel pomiaryWczesnorozwojoweJP;
    //private JPanel wykresyWczesnorozwojoweJP;
    private JButton usuńWizytęButton;
    private JButton usuńPacjentaButton;
    private JTextField wiekTF;
    private JLabel cisnienieL;
    private JLabel obwodKlatkiL;
    private JLabel obwodGlowyL;

    private Pacjent pacjent;
    private Pliki pliki;

    public JComboBox getListaPacjentow() {
        return listaPacjentow;
    }
    public JComboBox getListaWizyt() {
        return listaWizyt;
    }
    public Pacjent getPacjent() {
        return pacjent;
    }

    public Okno() {
        pliki = new Pliki();

        File listaFolderow = new File("./data");
        String [] dir = listaFolderow.list();
        for(int i=0; i<dir.length; i++) {
            listaPacjentow.addItem(dir[i]);
        }

        notatkiTA.setEditable(false);

        nowyPacjentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NowyPacjent onp = new NowyPacjent(Okno.this);
                onp.setVisible(true);
            }
        });

        wczytajPacjentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String pesel = (String) listaPacjentow.getSelectedItem();
                String daneFile = "./data/" + pesel + "/dane.txt";
                pacjent = new Pacjent(daneFile);
                odswiez();

                listaWizyt.removeAllItems();
                String daneFile1 = "./data/" + pesel + "/";
                File file = new File(daneFile1);
                String [] dirWizyty = file.list();
                for(int i=0; i<dirWizyty.length; i++){
                    if (dirWizyty[i].contains(".txt") || dirWizyty[i].contains(".jpeg") || dirWizyty[i].equals("personal_data")) {
                        continue;
                    }
                    listaWizyt.addItem(dirWizyty[i]);
                }

                wzrostTF.setText("");
                wagaTF.setText("");
                cisnienieTF.setText("");
                bmiTF.setText("");
                notatkiTA.setText("");
                obwodGlowyTF.setText("");
                obwodKlatkiTF.setText("");

                if (pacjent.getOkresRozwojowy().equals("dzieciństwa") || pacjent.getOkresRozwojowy().equals("dojrzewania")) {
                    //pomiaryWczesnorozwojoweJP.setEnabled(false);
                    //wykresyWczesnorozwojoweJP.setEnabled(false);
                    cisnienieL.setEnabled(true);
                    obwodGlowyL.setEnabled(false);
                    obwodKlatkiL.setEnabled(false);
                    cisnienieTF.setEnabled(true);
                    obwodGlowyTF.setEnabled(false);
                    obwodKlatkiTF.setEnabled(false);
                    obwGłowyButton.setEnabled(false);
                    obwKlatkiButton.setEnabled(false);
                    ciśnieniaButton.setEnabled(true);
                }
                else {
                    //pomiaryWczesnorozwojoweJP.setEnabled(true);
                    //wykresyWczesnorozwojoweJP.setEnabled(true);
                    //obwodKlatkiTF.setEnabled(true);
                    cisnienieL.setEnabled(false);
                    obwodGlowyL.setEnabled(true);
                    obwodKlatkiL.setEnabled(true);
                    cisnienieTF.setEnabled(false);
                    obwodGlowyTF.setEnabled(true);
                    obwodKlatkiTF.setEnabled(true);
                    obwGłowyButton.setEnabled(true);
                    obwKlatkiButton.setEnabled(true);
                    ciśnieniaButton.setEnabled(false);
                }
            }
        });

        nowaWizytaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    NowaWizyta nowaWizyta = new NowaWizyta(Okno.this);
                    nowaWizyta.setVisible(true);
                } catch (NullPointerException e) {
                    BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                    brakPacjenta.setVisible(true);
                }
            }
        });

        wczytajWizyteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listaWizyt.getItemCount() == 0) {
                    BrakWizyt brakWizyt = new BrakWizyt(Okno.this);
                    brakWizyt.setVisible(true);
                } else {
                    try {
                        String wizyta = (String) listaWizyt.getSelectedItem();
                        String pesel = (String) listaPacjentow.getSelectedItem();
                        String dane = "./data/" + pesel + "/" + wizyta;
                        if (pacjent.getOkresRozwojowy().equals("dzieciństwa") || pacjent.getOkresRozwojowy().equals("dojrzewania")) {
                            PomiaryDziecko pomiaryDziecko = new PomiaryDziecko(dane);
                            pacjent.setPomiaryDziecko(pomiaryDziecko);
                        } else {
                            PomiaryNiemowle pomiaryNiemowle = new PomiaryNiemowle(dane);
                            pacjent.setPomiaryNiemowle(pomiaryNiemowle);
                        }
                        odswiezWizyte();
                    } catch (NullPointerException e) {
                        BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                        brakPacjenta.setVisible(true);
                    }
                }
            }
        });

        usuńPacjentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String dirName = (String) listaPacjentow.getSelectedItem();
                String dirPacjentPath = "./data/" + dirName;

                pliki.usunFolder(dirPacjentPath);

                //ODSWIEZANIE COMBOBOXA 2 opcja, ta co w wizycie
                Object pacjentWybrany = listaPacjentow.getSelectedItem();
                listaPacjentow.removeItem(pacjentWybrany);

                //ODSWIEZANIE COMBOBOXA
                /*
                listaPacjentow.removeAllItems();
                File listaFolderow = new File("./data");
                String [] dir = listaFolderow.list();
                for(int i=0; i<dir.length; i++) {
                    if (dir[i].contains(".txt") || dir[i].contains(".jpeg")) {
                        continue;
                    }
                    listaPacjentow.addItem(dir[i]);
                }

                 */
                listaPacjentow.setEditable(false);
                wyczyscDaneWszystkie();
            }
        });

        usuńWizytęButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String dirName1 = (String) listaPacjentow.getSelectedItem();
                String dirName2 = (String) listaWizyt.getSelectedItem();
                String dirWizytaPath = "./data/" + dirName1 + "/" + dirName2;

                pliki.usunFolder(dirWizytaPath);

                //ODSWIEZANIE COMBOBOXA
                // inna opcja
                Object wizyta = listaWizyt.getSelectedItem();
                listaWizyt.removeItem(wizyta);
                listaWizyt.setEditable(false);

                 /*ODSWIEZANIE COMBOBOXA
                listaWizyt.removeAllItems();
                File listaFolderow = new File("./data/" + dirName1);
                String [] dir = listaFolderow.list();
                for(int i=0; i<dir.length; i++) {
                    if (dir[i].contains(".txt") || dir[i].contains(".jpeg")) {
                        continue;
                    }
                    listaWizyt.addItem(dir[i]);
                }
                listaWizyt.setEditable(false);

                  */
                 wyczyscDaneWizyta();
            }
        });

        //przyciski wykresy
        wzrostuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listaWizyt.getItemCount() == 0) {
                    BrakWizyt brakWizyt = new BrakWizyt(Okno.this);
                    brakWizyt.setVisible(true);
                } else {
                    try {
                        String path = (String) listaPacjentow.getSelectedItem();
                        String extraPathname = pacjent.typDanych();
                        String dataPath = "./data/" + path + "/personal_data/wzrost" + extraPathname + ".txt";
                        String destPath = "./data/" + path + "/personal_data/wzrost.jpeg";

                        pliki.generujSiatke(dataPath, destPath, pacjent.getOkresRozwojowy(), pacjent.getPlec(), 1);
                        wyswietlWykres(destPath);
                    } catch (NullPointerException e) {
                        BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                        brakPacjenta.setVisible(true);
                    }
                }
            }
        });

        wagiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listaWizyt.getItemCount() == 0) {
                    BrakWizyt brakWizyt = new BrakWizyt(Okno.this);
                    brakWizyt.setVisible(true);
                } else {
                    try {
                        String path = (String) listaPacjentow.getSelectedItem();
                        String extraPathname = pacjent.typDanych();
                        String dataPath = "./data/" + path + "/personal_data/waga" + extraPathname + ".txt";
                        String destPath = "./data/" + path + "/personal_data/waga.jpeg";

                        // plec 0 - chop, 1 - baba
                        pliki.generujSiatke(dataPath, destPath, pacjent.getOkresRozwojowy(), pacjent.getPlec(), 2);
                        wyswietlWykres(destPath);
                    } catch (NullPointerException e) {
                        BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                        brakPacjenta.setVisible(true);
                    }
                }
            }
        });

        bmiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listaWizyt.getItemCount() == 0) {
                    BrakWizyt brakWizyt = new BrakWizyt(Okno.this);
                    brakWizyt.setVisible(true);
                } else {
                    try {
                        String path = (String) listaPacjentow.getSelectedItem();
                        String extraPathname = pacjent.typDanych();
                        String dataPath = "./data/" + path + "/personal_data/bmi" + extraPathname + ".txt";
                        String destPath = "./data/" + path + "/personal_data/bmi.jpeg";

                        pliki.generujSiatke(dataPath, destPath, pacjent.getOkresRozwojowy(), pacjent.getPlec(), 3);
                        wyswietlWykres(destPath);
                    } catch (NullPointerException e) {
                        BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                        brakPacjenta.setVisible(true);
                    }
                }
            }
        });

        ciśnieniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listaWizyt.getItemCount() == 0) {
                    BrakWizyt brakWizyt = new BrakWizyt(Okno.this);
                    brakWizyt.setVisible(true);
                } else {
                    try {
                        String path = (String) listaPacjentow.getSelectedItem();
                        String extraPathname = pacjent.typDanych();
                        String dataPath = "./data/" + path + "/personal_data/cisnienie" + extraPathname + ".txt";
                        String destPath = "./data/" + path + "/personal_data/cisnienie.jpeg";

                        // plec 0 - chop, 1 - baba
                        pliki.generujSiatke(dataPath, destPath, pacjent.getOkresRozwojowy(), pacjent.getPlec(), 4);
                        wyswietlWykres(destPath);
                    } catch (NullPointerException e) {
                        BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                        brakPacjenta.setVisible(true);
                    }
                }
            }
        });

        obwGłowyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listaWizyt.getItemCount() == 0) {
                    BrakWizyt brakWizyt = new BrakWizyt(Okno.this);
                    brakWizyt.setVisible(true);
                } else {
                    try {
                        String path = (String) listaPacjentow.getSelectedItem();
                        String extraPathname = pacjent.typDanych();
                        String dataPath = "./data/" + path + "/personal_data/obwodglowy" + extraPathname + ".txt";
                        String destPath = "./data/" + path + "/personal_data/obwodglowy.jpeg";

                        // plec 0 - chop, 1 - baba
                        pliki.generujSiatke(dataPath, destPath, pacjent.getOkresRozwojowy(), pacjent.getPlec(), 5);
                        wyswietlWykres(destPath);
                    } catch (NullPointerException e) {
                        BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                        brakPacjenta.setVisible(true);
                    }
                }
            }
        });


        obwKlatkiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listaWizyt.getItemCount() == 0) {
                    BrakWizyt brakWizyt = new BrakWizyt(Okno.this);
                    brakWizyt.setVisible(true);
                } else {
                    try {
                        String path = (String) listaPacjentow.getSelectedItem();
                        String extraPathname = pacjent.typDanych();
                        String dataPath = "./data/" + path + "/personal_data/obwodklatki" + extraPathname + ".txt";
                        String destPath = "./data/" + path + "/personal_data/obwodklatki.jpeg";

                        // plec 0 - chop, 1 - baba
                        pliki.generujSiatke(dataPath, destPath, pacjent.getOkresRozwojowy(), pacjent.getPlec(), 6);
                        wyswietlWykres(destPath);
                    } catch (NullPointerException e) {
                        BrakPacjenta brakPacjenta = new BrakPacjenta(Okno.this);
                        brakPacjenta.setVisible(true);
                    }
                }
            }
        });
    }

    public void wyswietlWykres(String destPath){
        Wykres wykres = null;
        try {
            wykres = new Wykres(Okno.this, destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        wykres.setVisible(true);
    }

    public void odswiez()
    {
        nazwiskoTF.setText(pacjent.getNazwisko());
        imieTF.setText(pacjent.getImie());
        peselTF.setText(pacjent.getPesel());
        adresUlicaTF.setText(pacjent.getAdresUlica());
        adresMiastoTF.setText(pacjent.getAdresMiasto());
        dataUrodzeniaTF.setText(pacjent.getDataUrodzenia());
        okresRozwojowyTF.setText(pacjent.getOkresRozwojowy());
        if (pacjent.getPlec() == 1) {
            kobietaRadioButton.setEnabled(true);
            kobietaRadioButton.setSelected(true);
            mężczyznaRadioButton.setEnabled(false);
            mężczyznaRadioButton.setSelected(false);
        }else{
            mężczyznaRadioButton.setEnabled(true);
            mężczyznaRadioButton.setSelected(true);
            kobietaRadioButton.setEnabled(false);
            kobietaRadioButton.setSelected(false);
        }
        double [] wiek = pacjent.obliczWiek();
        String wiekString = (int)wiek[0] + " lat " + (int)wiek[1] + " miesięcy";
        wiekTF.setText(wiekString);
    }

    public void odswiezWizyte() {
        if (pacjent.getOkresRozwojowy().equals("dzieciństwa") || pacjent.getOkresRozwojowy().equals("dojrzewania")) {
            wzrostTF.setText(Double.toString(pacjent.getPomiaryDziecko().getWzrost()));
            wagaTF.setText(Double.toString(pacjent.getPomiaryDziecko().getWaga()));
            cisnienieTF.setText(pacjent.getPomiaryDziecko().getCisnienie());
            bmiTF.setText(Double.toString(pacjent.getPomiaryDziecko().getBmi()));
            notatkiTA.setText(pacjent.getPomiaryDziecko().getNotatki());
        }
        else {
            wzrostTF.setText(Double.toString(pacjent.getPomiaryNiemowle().getWzrost()));
            wagaTF.setText(Double.toString(pacjent.getPomiaryNiemowle().getWaga()));
            bmiTF.setText(Double.toString(pacjent.getPomiaryNiemowle().getBmi()));
            notatkiTA.setText(pacjent.getPomiaryNiemowle().getNotatki());
            obwodGlowyTF.setText(Double.toString(pacjent.getPomiaryNiemowle().getObwodGlowy()));
            obwodKlatkiTF.setText(Double.toString(pacjent.getPomiaryNiemowle().getObwodKlatki()));
        }
    }

    public void wyczyscDaneWizyta() {
        wzrostTF.setText("");
        wagaTF.setText("");
        cisnienieTF.setText("");
        bmiTF.setText("");
        notatkiTA.setText("");
        obwodGlowyTF.setText("");
        obwodKlatkiTF.setText("");
    }

    public void wyczyscDaneWszystkie() {
        wyczyscDaneWizyta();
        nazwiskoTF.setText("");
        imieTF.setText("");
        peselTF.setText("");
        adresUlicaTF.setText("");
        adresMiastoTF.setText("");
        dataUrodzeniaTF.setText("");
        okresRozwojowyTF.setText("");

        //JLabels
        cisnienieL.setEnabled(true);
        obwodGlowyL.setEnabled(true);
        obwodKlatkiL.setEnabled(true);
        //JTextfields
        cisnienieTF.setEnabled(true);
        obwodGlowyTF.setEnabled(true);
        obwodKlatkiTF.setEnabled(true);
        //JButtons
        ciśnieniaButton.setEnabled(true);
        obwGłowyButton.setEnabled(true);
        obwKlatkiButton.setEnabled(true);
        //RadioButtons
        kobietaRadioButton.setEnabled(true);
        mężczyznaRadioButton.setEnabled(true);
        kobietaRadioButton.setSelected(false);
        mężczyznaRadioButton.setSelected(false);

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Okno");
        Okno okno = new Okno();
        frame.setContentPane(okno.main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
