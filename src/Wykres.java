import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wykres extends JFrame{
    private JPanel wykres;
    private JLabel picLabel;

    public Wykres(Okno okno, String pathname) throws IOException {
        setContentPane(wykres);
        pack();
        BufferedImage myPicture = ImageIO.read(new File(pathname));
        ImageIcon myIcon = new ImageIcon(myPicture);
        int height = myIcon.getIconHeight() + 40;
        int width = myIcon.getIconWidth() + 18;
        picLabel.setIcon(myIcon);
        Dimension dim = new Dimension(width, height);
        this.setMinimumSize(dim);
    }
}
