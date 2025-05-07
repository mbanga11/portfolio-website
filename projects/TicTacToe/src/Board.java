
import java.util.*;

/**
 * This class keeps track of the board and controls access to the board's
 * pieces.
 * 
 * It has a copy constructor.
 * 
 * It can:
 * make a move on the board (place a piece)
 * give a list of moves available yet to be made
 * display itself via toString()
 * determine whose move it is
 * determine if there is a winner or a draw
 * 
 * @author ---Manraj Banga ---
 *
 */
public class Board {
    // These are used by getWinner() method.
    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = 2;
    public static final int DRAW_GAME = 0;
    public static final int NO_WINNER = 3;

    /*
     * Your Board class should have very few instance fields.
     * All instance fields will be private.
     * 
     * The board HAS-A storage for all the X & O pieces on the board.
     * Students should write comments that explain how an X vs O is represented.
     * 
     * The board class may HAS-A count of the X's and O's pieces on the board.
     * How you store the pieces on the board can make your code simple or complex.
     * 
     * 
     * Think about how you want to represent the board.
     * Here are 6 typical ways that students have stored the board:
     * - String
     * - int[]
     * - int[][]
     * - String[]
     * - String[][]
     * - char[][]
     * 
     * That's it. No more instance fields!
     */

  
     private char[][] board; 
     int moveCount ;



    /**
     * This constructor will create an empty board.
     */

    public Board() {
       //this creates the empty baord 
            board = new char[3][3];
            for (char[] row : board) {
                Arrays.fill(row, ' ');
            }
       // makes count 0; 
            moveCount = 0;
    }

    /**
     * The Copy Constructor will do a DEEP copy of an existing Board.
     * 
     * @param copyMe The Board to copy.
     */
    public Board(Board copyMe) {
            this.board = new char[3][3];

            for (int i = 0; i < 3; i++) {
                // Deep copy each row
                this.board[i] = Arrays.copyOf(copyMe.board[i], 3); 
            }

            this.moveCount = copyMe.moveCount;
        }

    /**
     * Gets who will move next: "X" or "O"
     * 
     * @return "X" or "O"
     */
    public String getNextMove() {
        if (moveCount % 2 == 0) {
            return "X"; 
        } else {
            return "O";
        }
    }
    

    // The board must be able to print itself nicely.
    public String toString() {
        // TODO: Student must implement this


    String result = "";
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            result += " " + board[i][j] + " ";
            if (j < 2) result += "|"; // Column separator
        }
        result += "\n";
        if (i < 2) result += "---+---+---\n"; // Row separator
    }
    return result;
    }

    /**
     * Makes the requested move on the board.
     *
     * The board will know whose turn it is next. It will place the appropriate
     * piece, X or O. It just knows!
     *
     * @param location as {row, col}
     */
    public void doMove(int[] location) {

        int row =location [0]; 
        int col =location [1];

        // Validate that the row and column are within the board
        if (row < 0 || row >= 3) {
            throw new IllegalArgumentException("Invalid move: Out of bounds.");
        }
        if (col < 0 || col >= 3) {
            throw new IllegalArgumentException("Invalid move: Out of bounds.");
        }
        

        if (board[row][col] != ' ') {
            throw new IllegalArgumentException("Invalid move: Cell already occupied.");
        }

        // Determine whose turn it is and place the piece
        if (getNextMove().equals("X")) {
            board[row][col] = 'X';
        } else {
            board[row][col] = 'O';
        }
        

        // Increment the move count
             moveCount++;


        
        
    }

    /**
     * Gets all the moves one can make on this board as a 2D array.
     * If the game is over, this should return an empty array.
     * 
     * The first dimension is the move number (0-index). It must match exactly
     * how many moves are available to make. (i.e. it cannot always be of size 9.)
     * 
     * The second dimension is an array for { row, col }. For example, { 2, 1 } is
     * the bottom row, middle column.
     * 
     * @return 2D array of moves
     */
    public int[][] getAvailableMoves() {
        // TODO Auto-generated method stub
        // Count empty spaces
            int count = 0;
            for (int row = 0; row < 3; row++) {
                 for (int col = 0; col < 3; col++) {
                 if (board[row][col] == ' ') count++;
                 }
             }

             int[][] available = new int[count][2];
             int index = 0;
             for (int i = 0; i < 3; i++) {
                  for (int j = 0; j < 3; j++) {
                     if (board[i][j] == ' ') {
                        available[index][0] = i;
                        available[index][1] = j;
                        index++;
                        }
                     }
                 }

            return available;
            

        
    }

    /**
     * The board can figure out if there is a winner, or if the game
     * has ended in a draw.
     * Use the Board constants:
     * Board.PLAYER_X
     * Board.PLAYER_O
     * Board.DRAW_GAME
     * Board.GAME_NOT_OVER
     * 
     * @return the value of the winner, or Draw, or game not over
     */
    public int getWinner() {
        // TODO: implement the ability to determine a winner/draw.

            // Check rows for a winner
            for (int i = 0; i < 3; i++) {
                if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    if (board[i][0] == 'X') {
                        return PLAYER_X;
                    } else {
                        return PLAYER_O;
                    }
                }
            }
        
            // Check columns for a winner
            for (int i = 0; i < 3; i++) {
                if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                    if (board[0][i] == 'X') {
                        return PLAYER_X;
                    } else {
                        return PLAYER_O;
                    }
                }
            }
        
            // Check diagonal
            if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                if (board[0][0] == 'X') {
                    return PLAYER_X;
                } else {
                    return PLAYER_O;
                }
            }

            if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                if (board[0][2] == 'X') {
                    return PLAYER_X;
                } else {
                    return PLAYER_O;
                }
            }
        
            // If no winner but board is full, it's a draw
            if (moveCount == 9) {
                return DRAW_GAME;
            }
        
            // Otherwise, game is still ongoing
            return NO_WINNER;
        }
        
    

    /**
     * Determines if the game is over. Someone won. Or, it's a draw.
     * @return true of the game is over.
     */
    public boolean isGameOver() {
        // TODO: implement this.

        return getWinner() != NO_WINNER;
        
    }

}
