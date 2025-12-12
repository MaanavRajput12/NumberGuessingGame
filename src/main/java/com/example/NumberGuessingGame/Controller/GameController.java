package com.example.NumberGuessingGame.Controller;
import com.example.NumberGuessingGame.Model.GameState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class GameController {

    private static final int MAX_ATTEMPTS = 10;
    private static final int MAX_GUESS = 100;
    private static final int MIN_GUESS = 1;

    private GameState getOrCreateGameState(HttpSession session) {
        GameState gameState = (GameState) session.getAttribute("gameState");
        if (gameState == null) {
            gameState = new GameState();
            startNewRound(gameState);
            session.setAttribute("gameState", gameState);
        }
        return gameState;
    }

    private void startNewRound(GameState gameState) {
        gameState.setTarget(new Random().nextInt(MIN_GUESS, MAX_GUESS + 1));
        gameState.setAttemptsLeft(MAX_ATTEMPTS);
        gameState.setLastGuess(0);
        gameState.setLastFeedback(null);
        if (gameState.getRoundsPlayed() < 0) gameState.setRoundsPlayed(0);
    }

    private int calculatePoints(int attemptsLeft) {
        return Math.max(1, 10 + attemptsLeft);
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        GameState gameState = getOrCreateGameState(session);
        model.addAttribute("gameState", gameState);
        model.addAttribute("min", MIN_GUESS);
        model.addAttribute("max", MAX_GUESS);
        return "index";
    }

    @PostMapping("/guess")
    public String guess(@RequestParam("guess") int guess, HttpSession session, Model model) {

        GameState gameState = getOrCreateGameState(session);

        if (gameState.getAttemptsLeft() <= 0 ||
                (gameState.getLastFeedback() != null && gameState.getLastFeedback().startsWith("Correct"))) {

            gameState.setLastFeedback("Round over! Please start a new round.");
        } else {
            gameState.setLastGuess(guess);
            gameState.setAttemptsLeft(gameState.getAttemptsLeft() - 1);

            if (guess == gameState.getTarget()) {
                int points = calculatePoints(gameState.getAttemptsLeft());
                gameState.setScore(gameState.getScore() + points);
                gameState.setRoundsPlayed(gameState.getRoundsPlayed() + 1);
                gameState.setLastFeedback("Correct! You've earned " + points + " points. The number was " + gameState.getTarget());
            } else if (guess < gameState.getTarget()) {
                gameState.setLastFeedback("Too low! Try again.");
            } else {
                gameState.setLastFeedback("Too high! Try again.");
            }

            if (gameState.getAttemptsLeft() <= 0 && guess != gameState.getTarget()) {
                gameState.setRoundsPlayed(gameState.getRoundsPlayed() + 1);
                gameState.setLastFeedback("Used all attempts! The number was " + gameState.getTarget());
            }
        }

        session.setAttribute("gameState", gameState);
        model.addAttribute("gameState", gameState);
        model.addAttribute("min", MIN_GUESS);
        model.addAttribute("max", MAX_GUESS);

        return "index";
    }

    @PostMapping("/newround")
    public String newRound(HttpSession session) {
        GameState gameState = getOrCreateGameState(session);
        startNewRound(gameState);
        session.setAttribute("gameState", gameState);
        return "redirect:/";
    }

    @PostMapping("/restart")
    public String restart(HttpSession session) {
        GameState gameState = new GameState();
        startNewRound(gameState);
        session.setAttribute("gameState", gameState);
        return "redirect:/";
    }
}
