import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Sudoku {
    private static final int SIZE = 9;

    private final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private final int[][] board;
    private final int[][] board1;
    private final String zorlukTutucu;

    public Sudoku(String zorlukTutucu) {
        this.zorlukTutucu = zorlukTutucu;
        board = new int[SIZE][SIZE];
        board1 = new int[SIZE][SIZE];

        sudokuOlustur();
        copy();
        bosBirak();
    }

    private boolean gecerliMi(int x, int y, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[x][i] == num || board[i][y] == num) {
                return false;
            }
        }

        int kucuk3x = x - x % 3;
        int kucuk3y = y - y % 3;

        for (int i = kucuk3x; i < kucuk3x + 3; i++) {
            for (int j = kucuk3y; j < kucuk3y + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean kontrolluYazi() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (board[x][y] == 0) {
                    Collections.shuffle(nums);

                    for (int i = 0; i < SIZE; i++) {
                        int num = nums.get(i);

                        if (gecerliMi(x, y, num)) {
                            board[x][y] = num;

                            if (kontrolluYazi()) {
                                return true;
                            }

                            board[x][y] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void sudokuOlustur() {
        kontrolluYazi();
    }

    private void copy() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                board1[x][y] = board[x][y];
            }
        }
    }

    private void bosBirak() {
        Random random = new Random();
        int bosAdet;

        switch (zorlukTutucu) {
            case "kolay":
                bosAdet = 5;
                break;
            case "orta":
                bosAdet = 20;
                break;
            case "zor":
                bosAdet = 30;
                break;
            default:
                bosAdet = 5;
                break;
        }

        while (bosAdet > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (board1[row][col] != 0) {
                board1[row][col] = 0;
                bosAdet--;
            }
        }
    }

    public int[][] getBoard1() {
        return board1;
    }

    public int[][] getBoard() {
        return board;
    }

    public String kutuya(int x, int y) {
        if (board1[x][y] == 0) {
            return " ";
        }
        return String.valueOf(board1[x][y]);
    }

}