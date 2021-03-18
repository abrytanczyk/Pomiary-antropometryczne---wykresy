import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NowaWizyta extends JFrame {
    private JTextField nowyWzrostTF;
    private JTextField nowyWagaTF;
    private JTextField nowyObwódGłowyTF;
    private JPanel oknoWizyta;
    private JTextArea notatkiTxt;
    private JButton zapiszButton;
    private JButton anulujButton;
    private JTextField nowyObwódKlatkiTF;
    private JTextField ciśnienieTF;
    private JLabel cisnienieL;
    private JLabel obwodGlowyL;
    private JLabel obwodKlatkiL;

    public NowaWizyta(Okno okno) {
        setContentPane(oknoWizyta);
        pack();

        if (okno.getPacjent().getOkresRozwojowy().equals("dzieciństwa") || okno.getPacjent().getOkresRozwojowy().equals("dojrzewania")) {
            //JLabels
            obwodGlowyL.setEnabled(false);
            obwodKlatkiL.setEnabled(false);
            cisnienieL.setEnabled(true);
            //JTextfields
            nowyObwódGłowyTF.setEnabled(false);
            nowyObwódKlatkiTF.setEnabled(false);
            ciśnienieTF.setEnabled(true);

        }else{
            //JLabels
            obwodGlowyL.setEnabled(true);
            obwodKlatkiL.setEnabled(true);
            cisnienieL.setEnabled(false);
            //JTextfields
            nowyObwódGłowyTF.setEnabled(true);
            nowyObwódKlatkiTF.setEnabled(true);
            ciśnienieTF.setEnabled(false);
        }


        anulujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Pliki pliki = new Pliki();
                String dirPacjent = (String) okno.getListaPacjentow().getSelectedItem();
                String notatki = notatkiTxt.getText();
                String wzrost = nowyWzrostTF.getText();
                String waga = nowyWagaTF.getText();
                String pomiaryString = wzrost + "," + waga;
                try {
                    Double wzrostD = Double.parseDouble(wzrost);
                    Double wagaD = Double.parseDouble(waga);
                    if (okno.getPacjent().typDanych().equals("_wczesny")) {
                        String obwodGlowy = nowyObwódGłowyTF.getText();
                        String obwodKlatki = nowyObwódKlatkiTF.getText();
                        pomiaryString += "," + obwodGlowy + "," + obwodKlatki;
                        Double obwodGlowyD = Double.parseDouble(obwodGlowy);
                        Double obwodKlatkiD = Double.parseDouble(obwodKlatki);
                        PomiaryNiemowle pomiaryNiemowle = new PomiaryNiemowle(wzrostD, wagaD, notatki, obwodGlowyD, obwodKlatkiD);
                        okno.getPacjent().setPomiaryNiemowle(pomiaryNiemowle);
                    } else {
                        String[] cisnienie = ciśnienieTF.getText().split("/");
                        Double cisnienie1 = Double.parseDouble(cisnienie[0]);
                        Double cisnienie2 = Double.parseDouble(cisnienie[1]);
                        pomiaryString += "," + cisnienie1 + "," + cisnienie2;
                        PomiaryDziecko pomiaryDziecko = new PomiaryDziecko(wzrostD, wagaD, notatki, cisnienie1, cisnienie2);
                        okno.getPacjent().setPomiaryDziecko(pomiaryDziecko);
                    }

                    /////   DODAWANIE NOWEGO FOLDERU    /////
                    pliki.utworzFolderPlikiWizyta(dirPacjent, notatki, pomiaryString);
                    String pattern = "dd-MM-yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String dataDzisiaj = simpleDateFormat.format(new Date());
                    okno.getListaWizyt().addItem(dataDzisiaj);

                    /////   DOPISYWANIE POMIARÓW DO PLIKÓW  ///// - to by się przydało jeszcze wsadzić do klasy pomiary i tutaj tylko jakaś funkcja
                    if (okno.getPacjent().typDanych().equals("_wczesny")) {
                        okno.getPacjent().getPomiaryNiemowle().zapiszDoPliku(okno.getPacjent());
                    } else {
                        okno.getPacjent().getPomiaryDziecko().zapiszDoPliku(okno.getPacjent());
                    }

                    //zamykanie okna
                    dispose();

                } catch (NumberFormatException e){
                    BrakPomiarow brakPomiarow = new BrakPomiarow(NowaWizyta.this);
                    brakPomiarow.setVisible(true);
                }

            }
        });
    }
}
