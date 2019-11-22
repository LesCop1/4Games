package fr.bcecb.state;

import fr.bcecb.input.MouseButton;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Constants;

import java.util.concurrent.TimeUnit;

public class EndGameScreen extends ScreenState {
    private GuiElement replayButton;
    private GuiElement exitButton;

    private Constants.GameType gameType;
    private long time;
    private int score;
    private int moneyEarned;

    private int backgroundWidth;
    private int backgroundHeight;

    public EndGameScreen(StateManager stateManager, Constants.GameType gameType, long time, int moneyEarned) {
        super(stateManager, "end_game");
        this.gameType = gameType;
        this.time = time;
        this.moneyEarned = moneyEarned;
        this.score = calculateScore();
        if (gameType.getBestTime() < this.time) {
            gameType.setBestTime(this.time);
        }
        if (gameType.getBestScore() < score) {
            gameType.setBestScore(score);
        }
        Constants.BANKROLL += this.moneyEarned;
    }

    @Override
    public void initGui() {
        this.backgroundWidth = 200;
        this.backgroundHeight = 150;
        setBackgroundTexture(null);

        Text gameTypeTitle = new Text(1001, (width / 2f), (height / 2f) - 65f, true, this.gameType.getName() + " terminé !");

        Text timeTitle = new Text(1002, (width / 2f) - 80f, (height / 2f) - 40f, false, "Votre temps :", 0.7f);
        Text scoreTitle = new Text(1003, (width / 2f) - 80f, (height / 2f) - 25f, false, "Votre score :", 0.7f);
        Text moneyTitle = new Text(1004, (width / 2f) - 80f, (height / 2f) - 10f, false, "Votre argent gagné :", 0.7f);

        Text time = new Text(1005, (width / 2f) + 50f, (height / 2f) - 40f, false, longToTime(this.time), 0.7f);
        Text score = new Text(1006, (width / 2f) + 50f, (height / 2f) - 25f, false, Integer.toString(this.score), 0.7f);
        Text money = new Text(1007, (width / 2f) + 50f, (height / 2f) - 10f, false, intToMoney(this.moneyEarned), 0.7f);

        RoundedRectangle lineSeparation = new RoundedRectangle(1008, (width / 2f), (height / 2f), 180, 1, true, Constants.COLOR_GREY, Float.MAX_VALUE);

        Text bestTimeTitle = new Text(1009, (width / 2f) - 80f, (height / 2f) + 10f, false, "Meilleur temps :", 0.6f);
        Text bestScoreTitle = new Text(1010, (width / 2f) - 80f, (height / 2f) + 20f, false, "Meilleur score :", 0.6f);
        Text allMoneyTitle = new Text(1011, (width / 2f) - 80f, (height / 2f) + 30f, false, "Votre argent :", 0.6f);

        Text bestTime = new Text(1012, (width / 2f) + 50f, (height / 2f) + 10f, false, findBestTime(this.gameType), 0.6f);
        Text bestScore = new Text(1013, (width / 2f) + 50f, (height / 2f) + 20f, false, Integer.toString(gameType.getBestScore()), 0.6f);
        Text bankroll = new Text(1014, (width / 2f) + 50f, (height / 2f) + 30f, false, intToMoney(Constants.BANKROLL), 0.6f);

        RoundedRectangle lineSeparation2 = new RoundedRectangle(1015, (width / 2f), (height / 2f) + 35f, 180, 1, true, Constants.COLOR_GREY, Float.MAX_VALUE);

        this.replayButton = new Button(1016, (width / 2f) - (backgroundWidth / 4f), (height / 2f) + 55f, 75, 30, true, "Rejouer");

        this.exitButton = new Button(1017, (width / 2f) + (backgroundWidth / 4f), (height / 2f) + 55f, 75, 30, true, "Quitter");

        addGuiElement(gameTypeTitle, timeTitle, time, scoreTitle, score, moneyTitle, money, lineSeparation, bestTimeTitle, bestScoreTitle, bestScore, allMoneyTitle, bestTime, bankroll, lineSeparation2, replayButton, exitButton);
    }

    @Override
    public boolean shouldRenderBelow() {
        return true;
    }

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public int getBackgroundHeight() {
        return backgroundHeight;
    }

    private String findBestTime(Constants.GameType gameType) {
        long bestTime = gameType.getBestTime();
        return longToTime(bestTime);
    }

    private String longToTime(long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    private String intToMoney(int money) {
        return money + " " + Constants.MONEY_NAME_SHORT;
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == replayButton.getId()) {
            stateManager.popMultipleState(gameType.getNbState());
            return true;
        } else if (id == exitButton.getId()) {
            stateManager.popMultipleState(gameType.getNbState() + 2);
            return true;
        }
        return false;
    }

    private int calculateScore() {
        return (int) Math.floor(this.moneyEarned * 3.4f);
    }
}
