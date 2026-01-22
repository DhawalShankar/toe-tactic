import java.util.*;

public class TicTacToe {

    static final int COMPUTER = 1;
    static final int HUMAN = 2;
    static final int SIDE = 3;

    static final char COMPUTERMOVE = 'O';
    static final char HUMANMOVE = 'X';

    static Scanner sc = new Scanner(System.in);

    static void showBoard(char[][] board) {
        System.out.println("\t\t\t " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
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

    static void initialise(char[][] board) {
        for (int i = 0; i < SIDE; i++)
            for (int j = 0; j < SIDE; j++)
                board[i][j] = '*';
    }

    static boolean rowCrossed(char[][] board) {
        for (int i = 0; i < SIDE; i++)
            if (board[i][0] == board[i][1] &&
                board[i][1] == board[i][2] &&
                board[i][0] != '*')
                return true;
        return false;
    }

    static boolean columnCrossed(char[][] board) {
        for (int i = 0; i < SIDE; i++)
            if (board[0][i] == board[1][i] &&
                board[1][i] == board[2][i] &&
                board[0][i] != '*')
                return true;
        return false;
    }

    static boolean diagonalCrossed(char[][] board) {
        if (board[0][0] == board[1][1] &&
            board[1][1] == board[2][2] &&
            board[0][0] != '*')
            return true;

        if (board[0][2] == board[1][1] &&
            board[1][1] == board[2][0] &&
            board[0][2] != '*')
            return true;

        return false;
    }

    static boolean gameOver(char[][] board) {
        return rowCrossed(board) || columnCrossed(board) || diagonalCrossed(board);
    }

    static int minimax(char[][] board, int depth, boolean isAI) {
        if (gameOver(board)) {
            return isAI ? -10 : 10;
        }

        if (depth == 9) return 0;

        if (isAI) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < SIDE; i++) {
                for (int j = 0; j < SIDE; j++) {
                    if (board[i][j] == '*') {
                        board[i][j] = COMPUTERMOVE;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = '*';
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < SIDE; i++) {
                for (int j = 0; j < SIDE; j++) {
                    if (board[i][j] == '*') {
                        board[i][j] = HUMANMOVE;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = '*';
                    }
                }
            }
            return best;
        }
    }

    static int bestMove(char[][] board, int moveIndex) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (board[i][j] == '*') {
                    board[i][j] = COMPUTERMOVE;
                    int score = minimax(board, moveIndex + 1, false);
                    board[i][j] = '*';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i * 3 + j;
                    }
                }
            }
        }
        return bestMove;
    }

    static void playTicTacToe(int turn) {
        char[][] board = new char[SIDE][SIDE];
        initialise(board);
        showInstructions();

        int moveIndex = 0;

        while (!gameOver(board) && moveIndex < 9) {
            if (turn == COMPUTER) {
                int n = bestMove(board, moveIndex);
                board[n / 3][n % 3] = COMPUTERMOVE;
                System.out.println("COMPUTER placed at " + (n + 1));
                showBoard(board);
                turn = HUMAN;
            } else {
                System.out.print("Enter position: ");
                int n = sc.nextInt() - 1;
                int x = n / 3, y = n % 3;

                if (n >= 0 && n < 9 && board[x][y] == '*') {
                    board[x][y] = HUMANMOVE;
                    showBoard(board);
                    turn = COMPUTER;
                } else {
                    System.out.println("Invalid move");
                    continue;
                }
            }
            moveIndex++;
        }

        if (!gameOver(board))
            System.out.println("It's a draw");
        else
            System.out.println((turn == COMPUTER ? "HUMAN" : "COMPUTER") + " has won");
    }

    public static void main(String[] args) {
        System.out.println("TIC TAC TOE\n");
        System.out.print("Do you want to start first? (y/n): ");
        char ch = sc.next().charAt(0);

        playTicTacToe(ch == 'y' ? HUMAN : COMPUTER);
    }
}
