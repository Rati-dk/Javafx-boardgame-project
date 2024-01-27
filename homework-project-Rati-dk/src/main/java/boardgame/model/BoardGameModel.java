package boardgame.model;


import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class BoardGameModel {
    public static final int BOARD_SIZE = 6;
    private  int emptyCellCount = BOARD_SIZE * BOARD_SIZE;
    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
    private boolean isPlayer1Turn;
    public BoardGameModel() {
        // Initialize the game board with empty squares
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.NONE);
            }
        }
        isPlayer1Turn = true;
    }

    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }

public void move(int i, int j, Player currentPlayer) {
    Square playerSymbol = currentPlayer == Player.PLAYER1 ? Square.X : Square.O;
    board[i][j].set(playerSymbol);
    emptyCellCount--;
    // Check if it is the last move and the current player wins
    if (emptyCellCount == 1) {
        // Determine the winner based on the current player
        Square winningSymbol = currentPlayer == Player.PLAYER1 ? Square.X : Square.O;
        // Handle the game-ending condition and declare the winner
        handleGameEnd(winningSymbol);
    }

}

    public void handleGameEnd(Square winningSymbol) {
        if (emptyCellCount == 1) {
            // Only one cell is left empty, indicating game end
            for (var i = 0; i < BOARD_SIZE; i++) {
                for (var j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j].get() == Square.NONE) {
                        // Found the last empty cell
                        Square winner = getOppositePlayerSymbol(board[(i+1)%BOARD_SIZE][j].get());
                        System.out.println("Game Over! Winner: " + winner);
                        return;
                    }
                }
            }
        } else if (emptyCellCount == 0) {
            // All cells are filled, indicating a draw
            System.out.println("Game Over! It's a draw.");
        }
        // If neither of the above conditions is met, the game is still ongoing
    }

    private Square getOppositePlayerSymbol(Square symbol) {
        return symbol == Square.X ? Square.O : Square.X;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var model = new BoardGameModel();
        System.out.println(model);
    }




}
