import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NowyPacjent extends JFrame{
    private JTextField nazwiskoTxt;
    private JTextField imieTxt;
    private JTextField peselTxt;
    private JRadioButton kobietaRadioButton;
    private JRadioButton mężczyznaRadioButton;
    private JTextField adresUlicaTF;
    private JButton zapiszButton;
    private JButton zamknijButton;
    private JPanel oknoPacjent;
    private JTextField adresMiastoTF;

    public NowyPacjent(Okno okno) {
        setContentPane(oknoPacjent);
        pack();

        ButtonGroup grupaPlecRB = new ButtonGroup();

        grupaPlecRB.add(mężczyznaRadioButton);
        grupaPlecRB.add(kobietaRadioButton);
        /*
        kobietaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                okno.getPacjent().setPlec(1);
            }
        });
        mężczyznaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                okno.getPacjent().setPlec(0);
            }
        });
*/
        zamknijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nazwisko = nazwiskoTxt.getText();
                String imie = imieTxt.getText();
                String pesel = peselTxt.getText();

                String adresUlica = adresUlicaTF.getText();
                String adresMiasto = adresMiastoTF.getText();

                int plec = 0;
                if (kobietaRadioButton.isSelected()){
                    plec = 1;
                }

                if(nazwisko.isEmpty() || imie.isEmpty() || pesel.isEmpty() || (!kobietaRadioButton.isSelected() && !mężczyznaRadioButton.isSelected())) {
                    BrakDanychPacjenta brakDanychPacjenta = new BrakDanychPacjenta(NowyPacjent.this);
                    brakDanychPacjenta.setVisible(true);
                    throw new BrakDanychException();
                }
                else {
                    Pliki pliki = new Pliki();
                    pliki.utworzFolderPacjent(pesel, nazwisko, imie);
                    pliki.utworzPlikDane(nazwisko, imie, pesel, plec, adresUlica, adresMiasto);
                    String pathName = nazwisko + " " + imie + " [" + pesel + "]";

                    //Pacjent pacjent = new Pacjent(nazwisko, imie, pesel, plec);
                    okno.getListaPacjentow().addItem(pathName);
                    dispose();
                }
            }
        });
    }
}
