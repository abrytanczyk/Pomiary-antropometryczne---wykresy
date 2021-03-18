import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrakWizyt extends JFrame{
    private JButton okButton;
    private JPanel panel;

    public BrakWizyt(Okno okno) {
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
