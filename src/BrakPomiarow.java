import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrakPomiarow  extends JFrame{
    private JButton okButton;
    private JTextArea textArea;
    private JPanel panel;

    public BrakPomiarow(NowaWizyta nowaWizyta) {
        setContentPane(panel);
        pack();

        textArea.setEditable(false);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
