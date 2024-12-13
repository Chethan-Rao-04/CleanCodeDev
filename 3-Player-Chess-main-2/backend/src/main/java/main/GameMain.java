package main;

import abstraction.IGameInterface;
import common.GameState;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GameMain implements IGameInterface {
    private String currentPlayer;
    private Map<String, String> board;
    private String playerWhite;
    private String playerBlack;
    private Stack<String> moveHistory;  // Track move history for undo functionality
    private boolean gameOver;
    private String winner;

    public GameMain() {
        // Initialize the fields
        board = new HashMap<>();
        moveHistory = new Stack<>();
        currentPlayer = "White";  // White goes first
        gameOver = false;
        winner = null;
    }

    @Override
    public void startGame(String playerWhite, String playerBlack) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        initializeBoard();
    }

    @Override
    public String getCurrentPlayer() {
        return currentPlayer;  // Assuming currentPlayer is a field in GameMain
    }

    private void initializeBoard() {
        // Initializing pieces on the board with some simplified examples
        board.put("a1", "Rw");
        board.put("b1", "Nw");
        board.put("c1", "Bw");
        board.put("d1", "Qw");
        board.put("e1", "Kw");
        board.put("f1", "Bw");
        board.put("g1", "Nw");
        board.put("h1", "Rw");
        board.put("a2", "Pw");
        board.put("b2", "Pw");
        board.put("c2", "Pw");
        board.put("d2", "Pw");
        board.put("e2", "Pw");
        board.put("f2", "Pw");
        board.put("g2", "Pw");
        board.put("h2", "Pw");

        // Black pieces
        board.put("a8", "Rb");
        board.put("b8", "Nb");
        board.put("c8", "Bb");
        board.put("d8", "Qb");
        board.put("e8", "Kb");
        board.put("f8", "Bb");
        board.put("g8", "Nb");
        board.put("h8", "Rb");
        board.put("a7", "Pb");
        board.put("b7", "Pb");
        board.put("c7", "Pb");
        board.put("d7", "Pb");
        board.put("e7", "Pb");
        board.put("f7", "Pb");
        board.put("g7", "Pb");
        board.put("h7", "Pb");

        // Empty squares (simplified for now)
        // No need to explicitly define all empty squares, just assume other squares are empty
    }

    @Override
    public void makeMove(String player, String fromPosition, String toPosition) {
        if (gameOver) {
            System.out.println("Game is over.");
            return;
        }

        if (!player.equals(currentPlayer)) {
            System.out.println("It's not your turn.");
            return;
        }

        // Check if the move is valid (for simplicity, we assume all moves are valid for now)
        if (!board.containsKey(fromPosition)) {
            System.out.println("Invalid from position.");
            return;
        }

        // Handle the move (we move the piece)
        String piece = board.get(fromPosition);
        board.put(toPosition, piece);
        board.remove(fromPosition);

        // Save move to history for undo
        moveHistory.push(fromPosition + "-" + toPosition);

        // Switch player turn
        currentPlayer = currentPlayer.equals("White") ? "Black" : "White";

        updateBoard();
    }

    @Override
    public List<String> getValidMoves(String position) {
        // Return a list of valid moves for a given piece (simplified for now)
        return new ArrayList<>();
    }

    @Override
    public void updateBoard() {
        // Print the current board state (can be replaced with a UI rendering logic)
        System.out.println("Current board state:");
        for (String position : board.keySet()) {
            System.out.println(position + ": " + board.get(position));
        }
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public String getWinner() {
        return winner;
    }

    @Override
    public void undoMove() {
        if (!moveHistory.isEmpty()) {
            String lastMove = moveHistory.pop();
            String[] positions = lastMove.split("-");
            String fromPosition = positions[0];
            String toPosition = positions[1];

            // Undo the last move
            String piece = board.get(toPosition);
            board.put(fromPosition, piece);
            board.remove(toPosition);

            // Switch player turn back
            currentPlayer = currentPlayer.equals("White") ? "Black" : "White";

            updateBoard();
        }
    }

    @Override
    public GameState onClick(String polygonText) {
        // Define highlightSquares here, or if not needed, remove it
        List<String> highlightSquares = new ArrayList<>();  // Placeholder for highlighting logic

        // Return a new GameState with the current state of the board and other details
        return new GameState(board, highlightSquares, gameOver, winner);
    }

    @Override
    public Map<String, String> getBoard() {
        return Map.of();
    }
}
