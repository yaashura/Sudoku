import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Arayuz extends Sudoku {
    ImageIcon resim = new ImageIcon("image/board.png");
    ImageIcon icon = new ImageIcon("image/ikon.png");
    File file = new File("Sonuçlar.txt");

    JFrame framee = new JFrame("Sudoku");
    JLayeredPane layeredPane = new JLayeredPane();

    JLabel label = new JLabel();
    JMenu menu = new JMenu("Yeni Oyun");
    JMenuBar menuBar = new JMenuBar();

    JButton[][] buton;
    JButton[] degerler;

    private int degerTutucu;
    private int[][] sayilar;

    JLabel zamanTablosu;
    Timer zamanlayici;
    int kalanZaman;
    int tumZaman;

    String zorlukTutucu;

    Arayuz(String zorlukTutucu) {
        super(zorlukTutucu);
        this.zorlukTutucu = zorlukTutucu;

        buton = new JButton[9][9];
        degerler = new JButton[9];
        sayilar = new int[9][9];

        menu();
        baslaGeriSayim();
        ekran();
        tahtadakiSayilar();
        degerler();

        framee.setVisible(true);
    }

    private void ekran() {
        framee.setIconImage(icon.getImage());
        framee.setLayout(null);
        framee.setMinimumSize(new Dimension(1000, 800));
        framee.setBounds(250, 11, 1000, 800);
        framee.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane.setBounds(0, 0, 1000, 800);
        framee.setContentPane(layeredPane);

        ImageIcon backgroundIcon = new ImageIcon("image/giris_arka.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledBackgroundImg = backgroundImg.getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledBackgroundImg);

        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 1000, 800);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        label.setBounds(35, -32, 800, 800);
        label.setIcon(resim);
        layeredPane.add(label, Integer.valueOf(1));

        zamanTablosu.setBounds(820, 20, 150, 30);
        zamanTablosu.setFont(new Font("Arial", Font.BOLD, 16));
        zamanTablosu.setForeground(Color.WHITE);
        layeredPane.add(zamanTablosu, Integer.valueOf(1));
    }

    private void degerler() {
        for (int x = 0; x < 9; x++) {
            degerler[x] = new JButton();
            degerler[x].setBounds(820, 60 + x * 70, 50, 50);

            String str = String.valueOf(x + 1);
            degerler[x].setText(str);
            degerler[x].setBorderPainted(false);
            degerler[x].setBackground(new Color(255, 219, 88));
            degerler[x].setFont(new Font("Arial", Font.BOLD, 20));

            int finalX = x;
            degerler[x].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int k = 0; k < 9; k++) {
                        if (k == finalX) {
                            degerTutucu = finalX + 1;
                            degerler[k].setBackground(Color.WHITE);
                        } else {
                            degerler[k].setBackground(new Color(255, 219, 88));
                        }
                    }
                }
            });

            layeredPane.add(degerler[x], Integer.valueOf(1));
        }
    }

    private void tahtadakiSayilar() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                buton[x][y] = new JButton();
                buton[x][y].setBackground(Color.WHITE);
                buton[x][y].setBounds(41 + 85 * x, 40 + 74 * y, 80, 68);
                buton[x][y].setBorderPainted(false);
                buton[x][y].setFont(new Font("Arial", Font.BOLD, 30));

                sayilar[x][y] = getBoard1()[x][y];
                buton[x][y].setText(kutuya(x, y));
                buton[x][y].setOpaque(false);
                buton[x][y].setContentAreaFilled(false);

                int finalY = y;
                int finalX = x;

                buton[x][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int yy = 0; yy < 9; yy++) {
                            for (int xx = 0; xx < 9; xx++) {
                                if (sayilar[xx][yy] != getBoard()[xx][yy]) {
                                    if (yy == finalY && xx == finalX) {
                                        String str = String.valueOf(degerTutucu);
                                        buton[xx][yy].setText(str);
                                        sayilar[xx][yy] = degerTutucu;
                                    }

                                    if (sayilar[xx][yy] != getBoard()[xx][yy]) {
                                        buton[xx][yy].setForeground(Color.RED);
                                    } else {
                                        buton[xx][yy].setForeground(new Color(0, 190, 0));
                                    }

                                    if (kontrol()) {
                                        zamanlayici.stop();
                                        framee.dispose();

                                        int gecenSure = tumZaman - kalanZaman;
                                        int minutes = gecenSure / 60;
                                        int seconds = gecenSure % 60;
                                        String timeString = String.format("%02d:%02d", minutes, seconds);

                                        BitisEkrani bitisEkrani = new BitisEkrani(timeString);
                                        bitisEkrani.basarili();
                                    }
                                }
                            }
                        }
                    }
                });

                layeredPane.add(buton[x][y], Integer.valueOf(2));
            }
        }
    }

    private void baslaGeriSayim() {
        zamanTablosu = new JLabel();

        switch (zorlukTutucu) {
            case "kolay":
                kalanZaman = 60;
                tumZaman = 60;
                zamanTablosu.setText("Kalan Süre: 01:00");
                break;
            case "orta":
                kalanZaman = 120;
                tumZaman = 120;
                zamanTablosu.setText("Kalan Süre: 02:00");
                break;
            case "zor":
                kalanZaman = 180;
                tumZaman = 180;
                zamanTablosu.setText("Kalan Süre: 03:00");
                break;
            default:
                kalanZaman = 60;
                tumZaman = 60;
                zamanTablosu.setText("Kalan Süre: 01:00");
                break;
        }

        zamanlayici = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kalanZaman--;

                int minutes = kalanZaman / 60;
                int seconds = kalanZaman % 60;
                String timeString = String.format("%02d:%02d", minutes, seconds);

                zamanTablosu.setText("Kalan Süre: " + timeString);

                if (kalanZaman <= 0) {
                    zamanlayici.stop();

                    JOptionPane.showMessageDialog(
                            framee,
                            "Zaman doldu oyun burada biter!"
                    );

                    framee.dispose();
                    new GirisEkrani();
                }
            }
        });

        zamanlayici.start();
    }

    private boolean kontrol() {
        int a = 0;

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (sayilar[x][y] == getBoard()[x][y]) {
                    a++;
                }
            }
        }

        return a == 81;
    }

    public void setVisible(boolean a) {
        framee.setVisible(a);
    }

    public void menu() {
        JMenuItem yeni = new JMenuItem("Yeni oyun");
        menu.add(yeni);

        yeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zamanlayici.stop();
                framee.dispose();
                new GirisEkrani();

                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                    bufferedWriter.write(" - Yeni oyuna geçildi ");
                    bufferedWriter.newLine();
                } catch (IOException a) {
                    a.printStackTrace();
                }
            }
        });

        menuBar.add(menu);
        framee.setJMenuBar(menuBar);
    }
}