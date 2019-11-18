package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.RoundedRectangle;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;

import java.util.concurrent.TimeUnit;

public class EndGameState extends ScreenState {
    private Constants.GameType gameType;
    private long time;
    private int moneyEarned;

    private int backgroundWidth;
    private int backgroundHeight;

    public EndGameState(Constants.GameType gameType, long time, int moneyEarned) {
        super("end_game", false);
        this.gameType = gameType;
        this.time = time;
        this.moneyEarned = moneyEarned;
    }

    @Override
    public void initGui() {
        this.backgroundWidth = 200;
        this.backgroundHeight = 150;

        Text gameTypeTitle = new Text(1001, (width / 2f), (height / 2f) - 65f, true, this.gameType.getName() + " terminé !");

        Text timeTitle = new Text(1002, (width / 2f) - 80f, (height / 2f) - 40f, false, "Votre temps :") {
            @Override
            public float getScale() {
                return 0.7f;
            }
        };
        Text moneyTitle = new Text(1003, (width / 2f) - 80f, (height / 2f) - 25f, false, "Votre argent gagné :") {
            @Override
            public float getScale() {
                return 0.7f;
            }
        };

        Text time = new Text(1004, (width / 2f) + 50f, (height / 2f) - 40f, false, longToTime(this.time)) {
            @Override
            public float getScale() {
                return 0.7f;
            }
        };
        Text money = new Text(1005, (width / 2f) + 50f, (height / 2f) - 25f, false, intToMoney(this.moneyEarned)) {
            @Override
            public float getScale() {
                return 0.7f;
            }
        };

        RoundedRectangle lineSeparation = new RoundedRectangle(1004, (width / 2f), (height / 2f) - 15f, 180, 1, true, Constants.COLOR_GREY, Float.MAX_VALUE);

        Text bestTimeTitle = new Text(1006, (width / 2f) - 80f, (height / 2f), false, "Meilleur temps :") {
            @Override
            public float getScale() {
                return 0.6f;
            }
        };
        Text allMoneyTitle = new Text(1007, (width / 2f) - 80f, (height / 2f) + 10f, false, "Votre argent :") {
            @Override
            public float getScale() {
                return 0.6f;
            }
        };

        Text bestTime = new Text(1008, (width / 2f) + 50f, (height / 2f), false, findBestTime(this.gameType)) {
            @Override
            public float getScale() {
                return 0.6f;
            }
        };
        Text bankroll = new Text(1009, (width / 2f) + 50f, (height / 2f) + 10f, false, intToMoney(Constants.BANKROLL)) {
            @Override
            public float getScale() {
                return 0.6f;
            }
        };

        RoundedRectangle lineSeparation2 = new RoundedRectangle(1010, (width / 2f), (height / 2f) + 20f, 180, 1, true, Constants.COLOR_GREY, Float.MAX_VALUE);

        Button replayButton = new Button(1011, (width / 2f) - (backgroundWidth / 4f), (height / 2f) + 45f, 80, 35, true, "Rejouer", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popMultipleState(gameType.getNbState());
            }
        };

        Button exitButton = new Button(1012, (width / 2f) + (backgroundWidth / 4f), (height / 2f) + 45f, 80, 35, true, "Quitter", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popMultipleState(2 + gameType.getNbState());
            }
        };

        addGuiElement(gameTypeTitle, timeTitle, time, moneyTitle, money, lineSeparation, bestTimeTitle, allMoneyTitle, bestTime, bankroll, lineSeparation2, replayButton, exitButton);
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
        long bestTime = Constants.BEST_TIMES.get(gameType);
        return longToTime(bestTime);
    }

    private String longToTime(long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    private String intToMoney(int money) {
        return money + " " + Constants.MONEY_NAME_SHORT;
    }
}
