package main;

import abstraction.IGameInterface;
import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.InvalidMoveException;
import common.InvalidPositionException;
import common.GameState;
import common.Position;
import model.Board;
import utility.BoardAdapter;
import utility.Log;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static utility.BoardAdapter.calculateSquareId;

/**
 * Class containing the main logic of the backend.
 * the click inputs from the webapp is communicated with the backend.
 */
public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();
    private final Board board;
    private Position moveStartPos, moveEndPos;
    private Set<Position> highlightSquares;

    /**
     * GameMain Constructor. Entry point to the backend logic
     * */
    public GameMain() {
        Log.d(TAG, "initGame GameMain()");
        board = new Board();
        moveStartPos = null;
        moveEndPos = null;
        highlightSquares = ImmutableSet.of();
    }

    /**
     * Get the current board map being used by backend for current game session
     * @return Board map
     * */
    @Override
    public Map<String, String> getBoard() {
        return board.getWebViewBoard();
    }

    /**
     * Responsible for sending mouse click events to backend and apply game logic over it to display
     * updated board layout to player.
     * @param  squareLabel The unique label of the square which is clicked by player
     * @return GameState which contains current game board layout and list of squares to highlight
     **/
    @Override
    public GameState onClick(String squareLabel) {
        try {
            // squarePos must be in range [0, 63]
            Log.d(TAG, ">>> onClick called: squareLabel: " + squareLabel);
            int squarePos = calculateSquareId(squareLabel);
            Log.d(TAG, ">>> onClick called: squarePos: " + squarePos);

            Position position = Position.get(squarePos / 8 == 0 ? Colour.WHITE : Colour.BLACK, (squarePos % 8), (squarePos % 8));
            if (board.isCurrentPlayersPiece(position)) { // player selects his own piece - first move
                moveStartPos = position;
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos);
                highlightSquares = board.getPossibleMoves(moveStartPos);
                if (highlightSquares.isEmpty()) { // Selected piece has no square to move, reset selection
                    moveStartPos = null;
                }
            } else if (moveStartPos != null) {
                moveEndPos = Position.get(squarePos / 8 == 0 ? Colour.WHITE : Colour.BLACK, (squarePos % 8), (squarePos % 8));
                board.move(moveStartPos, moveEndPos);
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos + ", moveEndPos: " + moveEndPos);

                moveStartPos = moveEndPos = null;
                highlightSquares = null;
            }
        } catch (InvalidMoveException e) {
            Log.e(TAG, "InvalidMoveException onClick: " + e.getMessage());
            moveStartPos = moveEndPos = null;
            highlightSquares = null;
        } catch (InvalidPositionException e) {
            Log.e(TAG, "InvalidPositionException onClick: " + e.getMessage());
            moveStartPos = moveEndPos = null;
            highlightSquares = null;
        }
        List<String> highlightSquaresList = BoardAdapter.convertHighlightSquaresToViewBoard(highlightSquares);
        GameState clickResponse = new GameState(getBoard(), highlightSquaresList);
        if (board.isGameOver()) {
            String winner = board.getWinner();
            Log.d(TAG, "Winner: " + winner);
            clickResponse.setGameOver(winner);
        }
        Log.d(TAG, "ClickResponse: " + clickResponse);
        return clickResponse;
    }

    /**
     * @return returns which colour turn it is currently
     * */
    @Override
    public Colour getTurn() {
        return board.getTurn();
    }
}