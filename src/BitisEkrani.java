import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BitisEkrani implements ActionListener {
    JFrame frame = new JFrame("Sudoku");
    ImageIcon iconic = new ImageIcon("image/ikon.png");
    JButton buton = new JButton("Yeni oyun");
    String zaman;

    public BitisEkrani(String zaman) {
        this.zaman = zaman;

        frame.setIconImage(iconic.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setBounds(400, 75, 800, 700);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buton) {
            frame.dispose();
            new GirisEkrani();
        }
    }

    public void basarili() {
        frame.getContentPane().removeAll();
        frame.setLayout(null);

        ImageIcon bgIcon = new ImageIcon("image/basari.png");
        Image bgImg = bgIcon.getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImg));
        background.setBounds(100, 80, 600, 500);
        background.setLayout(null);

        JLabel timeLabel = new JLabel("Bitirme Süreniz " + zaman);
        timeLabel.setBounds(100, 390, 400, 40);
        timeLabel.setFont(new Font("MV Boli", Font.PLAIN, 24));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK);

        buton.setBounds(235, 440, 130, 35);
        buton.setFocusable(false);
        buton.addActionListener(this);

        background.add(timeLabel);
        background.add(buton);

        frame.add(background);

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
}