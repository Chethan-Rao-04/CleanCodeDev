package abstraction;
import common.Colour;
import common.GameState;

import java.util.List;
import java.util.Map;

public interface IGameInterface {
    // Starts the game with two players
    void startGame(String playerWhite, String playerBlack);

    // Makes a move for the given player
    void makeMove(String player, String fromPosition, String toPosition);

    // Gets the current player's turn
    String getCurrentPlayer();

    // Gets the list of valid moves for a piece at a given position
    List<String> getValidMoves(String position);

    // Updates the board state (called after each move)
    void updateBoard();

    // Checks if the game is over
    boolean isGameOver();

    // Gets the winner of the game (if any)
    String getWinner();


    // Undoes the last move (optional feature)
    void undoMove();

    GameState onClick(String polygonText);

    Map<String, String> getBoard();
}
