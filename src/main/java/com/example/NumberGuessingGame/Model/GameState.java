package com.example.NumberGuessingGame.Model;

import java.io.Serializable;
public class GameState implements Serializable {
    private int target;
    private int attemptsLeft;
    private int lastGuess;
    private boolean isGameOver;
    private int score;
    private String lastFeedback;
    private int roundsPlayed;

    public GameState(){}

    public int getTarget() {
        return target;
    }
    public void setTarget(int target) {
        this.target = target;
    }

    public int getAttemptsLeft(){
        return attemptsLeft;
    }
    public void setAttemptsLeft(int attemptsLeft){
        this.attemptsLeft = attemptsLeft;
    }

    public int getLastGuess() {
        return lastGuess;
    }
    public void setLastGuess(int lastGuess) {
        this.lastGuess = lastGuess;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getLastFeedback() {
        return lastFeedback;
    }
    public void setLastFeedback(String lastFeedback) {
        this.lastFeedback = lastFeedback;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }
    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    
}
