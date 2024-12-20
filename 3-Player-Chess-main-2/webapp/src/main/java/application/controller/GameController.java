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

    @GetMapping("/newGame")
    public void handleNewGame() {
        System.out.println("New Game");
        this.game = new GameMain();
    }

    @PostMapping("/onClick")
    public GameState handleMove(@RequestBody String polygonText) throws InvalidPositionException {
        if (game == null) {
            throw new IllegalStateException("Game has not been started. Please start a new game first.");
        }
        System.out.println("Polygon: " + polygonText);
        return game.onClick(polygonText);
    }

    @GetMapping("/currentPlayer")
    public String handlePlayerTurn() {
        if (game == null) {
            throw new IllegalStateException("Game has not been started. Please start a new game first.");
        }
        System.out.println("Requesting current player");
        return game.getTurn().toString();
    }

    @GetMapping("/board")
    public Map<String, String> handleBoardRequest() {
        if (game == null) {
            throw new IllegalStateException("Game has not been started. Please start a new game first.");
        }
        return game.getBoard();
    }
}
