import java.util.*;

public class Main {

    public static void main(String[] args) {
        // verify that your board works first
        testBoard();

        // then see if the stats are correct
        printStats();
    }

    public static void printStats() {
        Board board = new Board();
        System.out.println("Total games: " + countGames(board));
        String[] type = { "Draws: ", "X wins: ", "O wins: " };
        int total = 0;
        // 0 == DRAW
        // 1 == X
        // 2 == O
        for (int winner = 0; winner < 3; winner++) {
            int count = countWins(board, winner);
            total += count;
            System.out.println(type[winner] + count);
        }
        System.out.println("Total games: " + total);
    }

    public static void testBoard() {

        Board board = new Board();
        while (!board.isGameOver()) {
            System.out.println("Current board:\n" + board);
            int[][] moves = board.getAvailableMoves();
            System.out.println("Available moves: " + Arrays.deepToString(moves));
            Board copy = new Board(board);
            // pick a random move to make
            int randMov = (int) (Math.random() * moves.length);
            int[] move = moves[randMov];
            System.out.printf("Making move (%d, %d)\n", move[0], move[1]);
            copy.doMove(move);
            // show boards after the move
            System.out.println("Original board (should be unchanged)\n" + board);
            System.out.println("Board after move (should be changed)\n" + copy);
            // continue to make moves by making current board be the copy
            board = copy;
        }
        String winner = "Draw";
        if (board.getWinner() == Board.PLAYER_X) {
            winner = "X";
        } else if (board.getWinner() == Board.PLAYER_O) {
            winner = "O";
        }
        System.out.printf("Winner is: %s\n", winner);
    }

    /**
     * Returns the count of remaining games this board can play out.
     * If the game is over and there are no more games possible, return 1.
     * 
     * Effectively, the number of remaining games for this board is:
     * remaining = summation of the games of the boards resulting from making each
     * of the available moves.
     * 
     * See the HW4 handout and slide decks for more information and guidance.
     * The most common issues are:
     * 1) The Board class is not implemented correctly
     * 2) An incorrect use or implementation of the Copy constructor.
     * 
     * @param board The current board to work with
     * @return count of remaining games the board can play
     */
    public static int countGames(Board board) {
        if (board.isGameOver()) {
            return 1; 
        }

        int totalGames = 0;
        int[][] moves = board.getAvailableMoves();

        for (int[] move : moves) {
            Board copy = new Board(board);
            copy.doMove(move);
            totalGames += countGames(copy);
        }

        return totalGames;
    }
    

    /**
     * Returns the count of Draws on this board when argument player == 0.
     * Returns the count of Wins on this board for X when argument player == 1.
     * Returns the count of Wins on this board for O when argument player == 2.
     * 
     * @param board  The current board to work with
     * @param player integer representation of the "player": 0=Draws. 1=X. 2=O
     * @return count of wins for the player
     */
    public static int countWins(Board board, int player) {
        if (board.isGameOver()) {
            if (board.getWinner() == player) {
                return 1;
            } else {
                return 0;
            }
        }
    
        int totalWins = 0;
        int[][] moves = board.getAvailableMoves();
    
        for (int[] move : moves) {
            Board copy = new Board(board);
            copy.doMove(move);
            totalWins += countWins(copy, player);
        }
    
        return totalWins;
    }
}
    