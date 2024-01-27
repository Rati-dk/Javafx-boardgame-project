package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.Player;
import boardgame.model.Square;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;



public class BoardGameController {
    private BoardGameModel model;
    @FXML
    private GridPane board;
    @FXML
    private TextField player1NameTextField;
    @FXML
    private TextField player2NameTextField;
    private boolean gameOver;
    boolean isPlayer1Turn = true;


    public BoardGameController() {
        this.model =new  BoardGameModel();
    }


    public void initialize() {
        gameOver = false;
        //promptForPlayerNames();
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);
        piece.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.squareProperty(i, j).get()) {
                            case NONE -> Color.TRANSPARENT;
                            case X -> Color.RED;
                            case O -> Color.BLUE;
                        };
                    }
                }
        );
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }
    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Click on square (%d,%d)%n", row, col);
        // Determine the current player based on the isPlayer1Turn variable
        Player currentPlayer = isPlayer1Turn ? Player.PLAYER1 : Player.PLAYER2;
        if (model.getSquare(row, col) != Square.NONE) {
            return; // Do nothing if the square is already filled
        }
        // Make a move based on the current player's turn
        model.move(row, col, currentPlayer);
        square.setDisable(true);
        isPlayer1Turn = !isPlayer1Turn;


    }


}

