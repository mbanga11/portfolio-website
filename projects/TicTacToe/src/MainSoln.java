import java.util.*;

public class MainSoln {

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
        if (board.getWinner() == 1) {
            winner = "X";
        } else if (board.getWinner() == 2) {
            winner = "O";
        }
        System.out.printf("Winner is: %s\n", winner);
    }

    public static int countGames(Board board) {
        if (board.isGameOver()) {
            return 1;
        }
        int count = 0;
        int[][] moves = board.getAvailableMoves();
        for (int[] move : moves) {
            Board child = new Board(board);
            child.doMove(move);
            count += countGames(child);
        }
        return count;
    }

    public static int countWins(Board board, int winner) {
        int won = board.getWinner();
        if (winner == won) {
            if (board.isGameOver()) {
                return 1;
            }
        } else if (board.isGameOver()) {
            // don't count this game since it doesn't match the winner we want
            return 0;
        }
        int count = 0;
        int[][] moves = board.getAvailableMoves();
        for (int[] move : moves) {
            Board child = new Board(board);
            child.doMove(move);
            count += countWins(child, winner);
        }
        return count;
    }
}
