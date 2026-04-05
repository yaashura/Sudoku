import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class GirisEkrani implements ActionListener {
    String[] zorluk = {"kolay", "orta", "zor"};
    File file = new File("Sonuçlar.txt");
    static String zorlukTutucu = "kolay";

    JFrame frame = new JFrame("Sudoku");
    JTextField isim = new JTextField();
    ImageIcon iconic = new ImageIcon("image/ikon.png");
    JButton buton = new JButton("Giriş");
    JComboBox<String> zorluklar = new JComboBox<>(zorluk);

    String adi;

    GirisEkrani() {
        frame.setIconImage(iconic.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(400, 75, 800, 700);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 700);
        frame.add(layeredPane);

        ImageIcon backgroundIcon = new ImageIcon("image/giris_arka.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledBackgroundImg = backgroundImg.getScaledInstance(800, 700, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledBackgroundImg);

        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 700);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        isim.setBounds(300, 400, 200, 30);
        zorluklar.setBounds(300, 450, 200, 30);
        buton.setBounds(300, 500, 200, 30);

        zorluklar.addActionListener(this);
        buton.addActionListener(this);

        layeredPane.add(isim, Integer.valueOf(1));
        layeredPane.add(zorluklar, Integer.valueOf(1));
        layeredPane.add(buton, Integer.valueOf(1));

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == zorluklar) {
            zorlukTutucu = (String) zorluklar.getSelectedItem();
            System.out.println(zorlukTutucu);
        }

        if (e.getSource() == buton) {
            adi = isim.getText();

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            try {
                if (adi.isEmpty()) {
                    throw new IllegalArgumentException();
                }

                Arayuz arayuz = new Arayuz(zorlukTutucu);
                frame.setVisible(false);
                arayuz.setVisible(true);
                frame.dispose();

                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(adi + " - " + zorlukTutucu);
                } catch (IOException a) {
                    a.printStackTrace();
                }

            } catch (IllegalArgumentException exception) {
                System.out.println("İsim boş bırakılamaz!!!");
                JOptionPane.showMessageDialog(null, "Kullanıcı adı boş bırakılamaz");
            }
        }
    }
}