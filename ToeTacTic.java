import java.util.*;

public class ToeTacTic {

    static final int COMPUTER = 1;
    static final int HUMAN = 2;
    static final int SIDE = 3;
    static final int MAX_MOVES = 3; // Maximum moves per player
    static final int TIME_LIMIT_SECONDS = 300; // 5 minutes per player
    static final int AVG_HUMAN_MOVE_TIME = 5000; // 5 seconds average per move

    static final char COMPUTERMOVE = 'O';
    static final char HUMANMOVE = 'X';
    static final char EMPTY = '*';

    static Scanner sc = new Scanner(System.in);

    // Track move history for each player
    static class MoveHistory {
        Queue<Integer> computerMoves = new LinkedList<>();
        Queue<Integer> humanMoves = new LinkedList<>();
    }

    // Timer class for each player (like chess clock)
    static class PlayerTimer {
        long remainingTime; // in milliseconds
        long turnStartTime;
        boolean isRunning;

        PlayerTimer(int seconds) {
            this.remainingTime = seconds * 1000L;
            this.isRunning = false;
        }

        void startTurn() {
            turnStartTime = System.currentTimeMillis();
            isRunning = true;
        }

        void endTurn() {
            if (isRunning) {
                long elapsed = System.currentTimeMillis() - turnStartTime;
                remainingTime -= elapsed;
                isRunning = false;
            }
        }

        boolean hasTimeLeft() {
            if (isRunning) {
                long elapsed = System.currentTimeMillis() - turnStartTime;
                return (remainingTime - elapsed) > 0;
            }
            return remainingTime > 0;
        }

        long getRemainingSeconds() {
            long remaining = remainingTime;
            if (isRunning) {
                long elapsed = System.currentTimeMillis() - turnStartTime;
                remaining -= elapsed;
            }
            return Math.max(0, remaining / 1000);
        }

        String getFormattedTime() {
            long seconds = getRemainingSeconds();
            long mins = seconds / 60;
            long secs = seconds % 60;
            return String.format("%d:%02d", mins, secs);
        }
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
        System.out.println("\n=============================================");
        System.out.println("   LIMITED MEMORY TIC TAC TOE WITH TIMER   ");
        System.out.println("=============================================");
        System.out.println("\nSpecial Rules:");
        System.out.println("- Only your last 3 moves stay on the board");
        System.out.println("- Your 4th move removes your oldest move");
        System.out.println("- Each player has 5:00 minutes total");
        System.out.println("- Computer uses ~5 seconds per move (fair play)");
        System.out.println("- Time runs out = You lose!\n");
        System.out.println("Choose a cell numbered from 1 to 9:\n");
        System.out.println("\t\t\t 1 | 2 | 3");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 4 | 5 | 6");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 7 | 8 | 9\n");
    }

    static void showTimers(PlayerTimer humanTimer, PlayerTimer computerTimer) {
        System.out.println("---------------------------------------------");
        System.out.println("  [TIME] YOU: " + humanTimer.getFormattedTime() + 
                         "  |  COMPUTER: " + computerTimer.getFormattedTime());
        System.out.println("---------------------------------------------");
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
                System.out.println("[REMOVED] " + (player == COMPUTERMOVE ? "COMPUTER" : "YOUR") + 
                                 " move at position " + (oldestMove + 1) + " disappeared!");
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
    static int minimax(char[][] board, int depth, boolean isAI, MoveHistory history, int maxDepth) {
        if (depth >= maxDepth) return 0;

        char winner = whoWon(board);
        if (winner == COMPUTERMOVE) return 10 - depth;
        if (winner == HUMANMOVE) return depth - 10;

        if (isAI) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                int x = i / 3, y = i % 3;
                if (board[x][y] == EMPTY) {
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
                
                int score = minimax(tempBoard, 0, false, tempHistory, 4);
                
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        
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
        PlayerTimer humanTimer = new PlayerTimer(TIME_LIMIT_SECONDS);
        PlayerTimer computerTimer = new PlayerTimer(TIME_LIMIT_SECONDS);
        
        initialise(board);
        showInstructions();

        int moveCount = 0;

        while (moveCount < 100) {
            showTimers(humanTimer, computerTimer);
            
            if (turn == COMPUTER) {
                // Start computer's timer
                computerTimer.startTurn();
                
                // Check if computer has time left
                if (!computerTimer.hasTimeLeft()) {
                    computerTimer.endTurn();
                    System.out.println("\n[TIMEOUT] COMPUTER ran out of time!");
                    System.out.println("[WINNER] YOU WIN by timeout!");
                    return;
                }
                
                System.out.println("[COMPUTER] Thinking...");
                
                // Calculate move
                int n = bestMove(board, history);
                if (n == -1) break;
                
                // Simulate thinking time (fair play)
                try {
                    Thread.sleep(AVG_HUMAN_MOVE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                makeMove(board, n, COMPUTERMOVE, history, true);
                System.out.println("[COMPUTER] Played at position " + (n + 1));
                
                // Stop computer's timer (includes the 5-second delay)
                computerTimer.endTurn();
                
                showBoard(board);
                
                if (whoWon(board) == COMPUTERMOVE) {
                    showTimers(humanTimer, computerTimer);
                    System.out.println("[WINNER] COMPUTER has won!");
                    return;
                }
                
                turn = HUMAN;

            } else {
                // Start human's timer
                humanTimer.startTurn();
                
                int n = -1;
                boolean validInput = false;
                
                while (!validInput) {
                    // Check if human has time left
                    if (!humanTimer.hasTimeLeft()) {
                        humanTimer.endTurn();
                        System.out.println("\n[TIMEOUT] YOU ran out of time!");
                        System.out.println("[WINNER] COMPUTER wins by timeout!");
                        return;
                    }
                    
                    try {
                        System.out.print("[YOUR TURN] Enter move (1-9): ");
                        n = sc.nextInt() - 1;
                        
                        if (n < 0 || n > 8 || board[n / 3][n % 3] != EMPTY) {
                            System.out.println("[ERROR] Invalid move. Try again.");
                            continue;
                        }
                        validInput = true;
                        
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println("[ERROR] Invalid input. Enter a number 1-9.");
                    }
                }

                makeMove(board, n, HUMANMOVE, history, true);
                
                // Stop human's timer
                humanTimer.endTurn();
                
                showBoard(board);
                
                if (whoWon(board) == HUMANMOVE) {
                    showTimers(humanTimer, computerTimer);
                    System.out.println("[WINNER] YOU WON!");
                    return;
                }
                
                turn = COMPUTER;
            }
            
            moveCount++;
        }

        showTimers(humanTimer, computerTimer);
        System.out.println("Game ended after " + moveCount + " moves. It's a draw!");
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        System.out.println("\n=============================================");
        System.out.println("   LIMITED MEMORY TIC TAC TOE WITH TIMER   ");
        System.out.println("=============================================\n");
        System.out.print("Do you want to start first? (y/n): ");
        char choice = sc.next().charAt(0);

        playTicTacToe(choice == 'y' ? HUMAN : COMPUTER);
        sc.close();
    }
}