import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrakDanychPacjenta extends JFrame{
    private JPanel panel;
    private JButton okButton;

    public BrakDanychPacjenta(NowyPacjent nowyPacjent) {
        setContentPane(panel);
        pack();
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
