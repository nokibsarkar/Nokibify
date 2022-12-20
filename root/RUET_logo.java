package root;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class RUET_logo extends Canvas {
    public void paint(Graphics g) {
        Toolkit t = Toolkit.getDefaultToolkit();
        Image img = t.getImage("/home/nokib/Data/Projects/Personal/OOP-Lab/Nokibify/root/RUET_logo.svg.png");
        g.drawImage(img, 0, 0, this);
    }

    public static void main(String[] args) {
        RUET_logo m = new RUET_logo();
        m.setSize(50, 50);
        m.setMaximumSize(new Dimension(50, 50));
        JFrame f = new JFrame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.out.println("Closing");
                System.exit(0);
            }
        });
        //f.add(m);
        try {
            BufferedImage myPicture = ImageIO.read(new File("/home/nokib/Data/Projects/Personal/OOP-Lab/Nokibify/root/RUET_logo.svg.png"));
            ImageIcon ico = new ImageIcon(myPicture.getScaledInstance(400, 400, Image.SCALE_SMOOTH));

            JLabel picLabel = new JLabel(ico);
            picLabel.setSize(new Dimension(50, 50));
            f.add(picLabel);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        }
        f.setSize(400, 400);
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }
}
