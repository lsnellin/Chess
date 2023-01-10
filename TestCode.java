import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestCode {
    public static void main(String[] args) {
        String fileName = "Piece_Icons/rookW.png";
        BufferedImage icon = null;

        try {
            icon = ImageIO.read(new File(fileName)); 
        }
        catch(IOException e) {
            e.printStackTrace();
        }


        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);

        JLabel picLabel = new JLabel(new ImageIcon(icon));

        frame.add(picLabel);

        frame.setVisible(true);
    }
}
