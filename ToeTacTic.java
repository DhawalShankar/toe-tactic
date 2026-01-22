import java.util.*;

public class ToeTacTic {

    static final int COMPUTER = 1;
    static final int HUMAN = 2;
    static final int SIDE = 3;
    static final int MAX_MOVES = 3; // Maximum moves per player

    static final char COMPUTERMOVE = 'O';
    static final char HUMANMOVE = 'X';
    static final char EMPTY = '*';

    static Scanner sc = new Scanner(System.in);

    // Track move history for each player
    static class MoveHistory {
        Queue<Integer> computerMoves = new LinkedList<>();
        Queue<Integer> humanMoves = new LinkedList<>();
    }

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
        System.out.println("\n=== LIMITED MEMORY TIC TAC TOE ===");
        System.out.println("Special Rule: Only your last 3 moves stay on the board!");
        System.out.println("When you make a 4th move, your oldest move disappears.\n");
        System.out.println("Choose a cell numbered from 1 to 9\n");
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

    // ---------- MOVE MANAGEMENT ----------
    static void makeMove(char[][] board, int position, char player, MoveHistory history, boolean showMessage) {
        Queue<Integer> moves = (player == COMPUTERMOVE) ? history.computerMoves : history.humanMoves;
        
        // If player already has MAX_MOVES on board, remove the oldest
        if (moves.size() >= MAX_MOVES) {
            int oldestMove = moves.poll();
            board[oldestMove / 3][oldestMove % 3] = EMPTY;
            if (showMessage) {
                System.out.println((player == COMPUTERMOVE ? "COMPUTER" : "HUMAN") + "'s move at position " + (oldestMove + 1) + " disappeared!");
            }
        }
        
        // Add new move
        board[position / 3][position % 3] = player;
        moves.add(position);
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

    // ---------- MINIMAX (Modified for limited memory) ----------
    static int minimax(char[][] board, int depth, boolean isAI, MoveHistory history, int maxDepth) {
        // Limit search depth for performance
        if (depth >= maxDepth) return 0;

        char winner = whoWon(board);
        if (winner == COMPUTERMOVE) return 10 - depth;
        if (winner == HUMANMOVE) return depth - 10;

        if (isAI) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                int x = i / 3, y = i % 3;
                if (board[x][y] == EMPTY) {
                    // Simulate move with history
                    char[][] tempBoard = copyBoard(board);
                    MoveHistory tempHistory = copyHistory(history);
                    makeMove(tempBoard, i, COMPUTERMOVE, tempHistory, false);
                    
                    best = Math.max(best, minimax(tempBoard, depth + 1, false, tempHistory, maxDepth));
                }
            }
            return best == Integer.MIN_VALUE ? 0 : best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                int x = i / 3, y = i % 3;
                if (board[x][y] == EMPTY) {
                    // Simulate move with history
                    char[][] tempBoard = copyBoard(board);
                    MoveHistory tempHistory = copyHistory(history);
                    makeMove(tempBoard, i, HUMANMOVE, tempHistory, false);
                    
                    best = Math.min(best, minimax(tempBoard, depth + 1, true, tempHistory, maxDepth));
                }
            }
            return best == Integer.MAX_VALUE ? 0 : best;
        }
    }

    static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++)
            copy[i] = board[i].clone();
        return copy;
    }

    static MoveHistory copyHistory(MoveHistory history) {
        MoveHistory copy = new MoveHistory();
        copy.computerMoves = new LinkedList<>(history.computerMoves);
        copy.humanMoves = new LinkedList<>(history.humanMoves);
        return copy;
    }

    static int bestMove(char[][] board, MoveHistory history) {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;

        for (int i = 0; i < 9; i++) {
            int x = i / 3, y = i % 3;
            if (board[x][y] == EMPTY) {
                char[][] tempBoard = copyBoard(board);
                MoveHistory tempHistory = copyHistory(history);
                makeMove(tempBoard, i, COMPUTERMOVE, tempHistory, false);
                
                int score = minimax(tempBoard, 0, false, tempHistory, 4); // Limited depth
                
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        
        // If no good move found, pick first available
        if (move == -1) {
            for (int i = 0; i < 9; i++)
                if (board[i / 3][i % 3] == EMPTY)
                    return i;
        }
        
        return move;
    }

    // ---------- GAME LOOP ----------
    static void playTicTacToe(int turn) {
        char[][] board = new char[SIDE][SIDE];
        MoveHistory history = new MoveHistory();
        initialise(board);
        showInstructions();

        int moveCount = 0;
        int lastPlayer = -1;

        while (moveCount < 100) { // Prevent infinite games
            
            if (turn == COMPUTER) {
                int n = bestMove(board, history);
                if (n == -1) break; // No moves available
                
                makeMove(board, n, COMPUTERMOVE, history, true);
                System.out.println("COMPUTER played at " + (n + 1));
                showBoard(board);
                
                if (whoWon(board) == COMPUTERMOVE) {
                    System.out.println("COMPUTER has won!");
                    return;
                }
                
                lastPlayer = COMPUTER;
                turn = HUMAN;

            } else {
                int n = -1;
                try {
                    System.out.print("Enter position (1-9): ");
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

                makeMove(board, n, HUMANMOVE, history, true);
                showBoard(board);
                
                if (whoWon(board) == HUMANMOVE) {
                    System.out.println("HUMAN has won!");
                    return;
                }
                
                lastPlayer = HUMAN;
                turn = COMPUTER;
            }
            
            moveCount++;
        }

        System.out.println("Game ended after " + moveCount + " moves. It's a draw!");
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        System.out.println("\n=== LIMITED MEMORY TIC TAC TOE ===\n");
        System.out.print("Do you want to start first? (y/n): ");
        char choice = sc.next().charAt(0);

        playTicTacToe(choice == 'y' ? HUMAN : COMPUTER);
        sc.close();
    }
}