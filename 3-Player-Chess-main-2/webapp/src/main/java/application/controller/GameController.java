package application.controller;

import abstraction.IGameInterface;
import common.InvalidPositionException;
import common.GameState;
import main.GameMain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GameController {
    private IGameInterface game;

    // Initialize a new game
    @GetMapping("/newGame")
    public void handleNewGame() {
        System.out.println("New Game");
        this.game = new GameMain(); // Start a new game
    }

    // Handle a move on the game board
    @PostMapping("/onClick")
    public GameState handleMove(@RequestBody String polygonText) throws InvalidPositionException {
        if (game == null) {
            throw new IllegalStateException("Game has not been started. Please start a new game first.");
        }
        System.out.println("Polygon: " + polygonText);
        return game.onClick(polygonText); // Handle move and return game state
    }

    // Get the current player's turn
    @GetMapping("/currentPlayer")
    public String handlePlayerTurn() {
        if (game == null) {
            throw new IllegalStateException("Game has not been started. Please start a new game first.");
        }
        System.out.println("Requesting current player");
        return game.getCurrentPlayer();  // Fetch current player
    }

    // Get the current game board state
    @GetMapping("/board")
    public Map<String, String> handleBoardRequest() {
        if (game == null) {
            throw new IllegalStateException("Game has not been started. Please start a new game first.");
        }
        return game.getBoard();  // Return the current board configuration
    }

    // Helper method to get the game instance (optional)
    public IGameInterface getGame() {
        return game;
    }
}
