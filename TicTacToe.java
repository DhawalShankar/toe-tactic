import java.util.*;

public class TicTacToe {

    static final int COMPUTER = 1;
    static final int HUMAN = 2;
    static final int SIDE = 3;

    static final char COMPUTERMOVE = 'O';
    static final char HUMANMOVE = 'X';
    static final char EMPTY = '*';

    static Scanner sc = new Scanner(System.in);

    // ---------- DISPLAY ----------
    static void showBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < SIDE; i++) {
            System.out.println("\t\t\t " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) System.out.println("\t\t\t-----------");
        }
        System.out.println();
    }

    static void showInstructions() {
        System.out.println("\nChoose a cell numbered from 1 to 9\n");
        System.out.println("\t\t\t 1 | 2 | 3");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 4 | 5 | 6");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 7 | 8 | 9\n");
    }

    // ---------- INIT ----------
    static void initialise(char[][] board) {
        for (int i = 0; i < SIDE; i++)
            Arrays.fill(board[i], EMPTY);
    }

    // ---------- GAME STATE ----------
    static boolean gameOver(char[][] board) {
        return whoWon(board) != EMPTY;
    }

    static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < SIDE; i++)
            for (int j = 0; j < SIDE; j++)
                if (board[i][j] == EMPTY)
                    return false;
        return true;
    }

    static char whoWon(char[][] board) {
        // Rows
        for (int i = 0; i < SIDE; i++)
            if (board[i][0] == board[i][1] &&
                board[i][1] == board[i][2] &&
                board[i][0] != EMPTY)
                return board[i][0];

        // Columns
        for (int i = 0; i < SIDE; i++)
            if (board[0][i] == board[1][i] &&
                board[1][i] == board[2][i] &&
                board[0][i] != EMPTY)
                return board[0][i];

        // Diagonals
        if (board[0][0] == board[1][1] &&
            board[1][1] == board[2][2] &&
            board[0][0] != EMPTY)
            return board[0][0];

        if (board[0][2] == board[1][1] &&
            board[1][1] == board[2][0] &&
            board[0][2] != EMPTY)
            return board[0][2];

        return EMPTY;
    }

    // ---------- MINIMAX ----------
    static int minimax(char[][] board, int depth, boolean isAI) {

        char winner = whoWon(board);
        if (winner == COMPUTERMOVE) return 10 - depth;
        if (winner == HUMANMOVE) return depth - 10;
        if (isBoardFull(board)) return 0; // Draw

        if (isAI) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                int x = i / 3, y = i % 3;
                if (board[x][y] == EMPTY) {
                    board[x][y] = COMPUTERMOVE;
                    best = Math.max(best, minimax(board, depth + 1, false));
                    board[x][y] = EMPTY;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                int x = i / 3, y = i % 3;
                if (board[x][y] == EMPTY) {
                    board[x][y] = HUMANMOVE;
                    best = Math.min(best, minimax(board, depth + 1, true));
                    board[x][y] = EMPTY;
                }
            }
            return best;
        }
    }

    static int bestMove(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;

        for (int i = 0; i < 9; i++) {
            int x = i / 3, y = i % 3;
            if (board[x][y] == EMPTY) {
                board[x][y] = COMPUTERMOVE;
                int score = minimax(board, 0, false);
                board[x][y] = EMPTY;

                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    // ---------- GAME LOOP ----------
    static void playTicTacToe(int turn) {
        char[][] board = new char[SIDE][SIDE];
        initialise(board);
        showInstructions();

        int lastPlayer = -1;

        while (!gameOver(board) && !isBoardFull(board)) {

            if (turn == COMPUTER) {
                int n = bestMove(board);
                board[n / 3][n % 3] = COMPUTERMOVE;
                System.out.println("COMPUTER played at " + (n + 1));
                showBoard(board);
                lastPlayer = COMPUTER;
                turn = HUMAN;

            } else {
                int n = -1;
                try {
                    System.out.print("Enter position: ");
                    n = sc.nextInt() - 1;
                } catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Invalid input. Enter a number.");
                    continue;
                }

                if (n < 0 || n > 8 || board[n / 3][n % 3] != EMPTY) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }

                board[n / 3][n % 3] = HUMANMOVE;
                showBoard(board);
                lastPlayer = HUMAN;
                turn = COMPUTER;
            }
        }

        if (whoWon(board) == EMPTY)
            System.out.println("It's a draw.");
        else
            System.out.println((lastPlayer == COMPUTER ? "COMPUTER" : "HUMAN") + " has won!");
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        System.out.println("\nTIC TAC TOE\n");
        System.out.print("Do you want to start first? (y/n): ");
        char choice = sc.next().charAt(0);

        playTicTacToe(choice == 'y' ? HUMAN : COMPUTER);
    }
}
