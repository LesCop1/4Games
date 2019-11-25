package fr.bcecb.batailleNavale;

import com.google.common.base.Stopwatch;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameScreen;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import fr.bcecb.util.MathHelper;

import java.util.concurrent.TimeUnit;

public class BattleshipScreen extends ScreenState {
    private static final ResourceHandle<Texture> defaultTexture = new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {};
    private static final ResourceHandle<Texture> sink = new ResourceHandle<>("textures/BatailleNavale/sink.png") {};
    private static final ResourceHandle<Texture> touch = new ResourceHandle<>("textures/BatailleNavale/touch.png") {};

    private Battleship battleship;
    private int currentPlayer;
    private boolean shoot = false;

    private Button changePlayerButton;
    private Stopwatch stopwatch;

    public BattleshipScreen(StateManager stateManager) {
        super(stateManager, "game_battleship");
        this.battleship = new Battleship();
        this.battleship.init();
        this.stopwatch = Stopwatch.createStarted();
    }

    @Override
    public void onEnter() {
        super.onEnter();
        stateManager.pushState(new FirstPhaseBattleshipScreen(stateManager, battleship));
    }

    @Override
    public void initGui() {
        setBackgroundTexture(Constants.BS_BACKGROUND);

        float btnSize = 14.99f;

        float x = (width / 2f) - (9 * btnSize / 2) - 4;
        for (int i = 0; i < Battleship.GRID_SIZE; i++, x += btnSize) {

            float y = (height / 2f) - (9 * btnSize / 2) - 4;
            for (int j = 0; j < Battleship.GRID_SIZE; j++, y += btnSize) {
                int finalI = i;
                int finalJ = j;
                Button caseButton = new Button(((10 * finalI) + finalJ), x, y, btnSize, btnSize, false) {
                    @Override
                    public boolean isDisabled() {
                        int value = battleship.getPlayerGrid(currentPlayer)[finalI][finalJ];
                        return shoot || value == Battleship.SUCCESS_HIT || value == Battleship.FAILED_HIT;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        int value = battleship.getPlayerGrid(currentPlayer)[finalI][finalJ];
                        return value == Battleship.SUCCESS_HIT ? touch : value == Battleship.FAILED_HIT ? sink : defaultTexture;
                    }

                    @Override
                    public ResourceHandle<Texture> getHoverTexture() {
                        return null;
                    }

                    @Override
                    public ResourceHandle<Texture> getDisabledTexture() {
                        return null;
                    }
                };
                addGuiElement(caseButton);
            }
        }

        float btnSize2 = 7.99f;

        float m = 5*(width / 6f) - (9 * btnSize2 / 2) - 4;
        for (int i = 0; i < Battleship.GRID_SIZE; i++, m += btnSize2) {

            float n = 5*(height / 6f) - (9 * btnSize2 / 2) - 4;
            for (int j = 0; j < Battleship.GRID_SIZE; j++, n += btnSize2) {
                int finalI = i;
                int finalJ = j;
                Button caseButton2 = new Button(((10 * finalI) + finalJ + 500), m, n, btnSize2, btnSize2, false) {
                    @Override
                    public boolean isDisabled() {
                        return true;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        int value = battleship.getNextPlayerGrid(currentPlayer)[finalI][finalJ];
                        return value == Battleship.SUCCESS_HIT ? touch : value == Battleship.FAILED_HIT ? sink : defaultTexture;
                    }

                    @Override
                    public ResourceHandle<Texture> getHoverTexture() {
                        return null;
                    }

                    @Override
                    public ResourceHandle<Texture> getDisabledTexture() {
                        return null;
                    }
                };
                addGuiElement(caseButton2);
            }
        }

        this.changePlayerButton = new Button(120, (width / 20f), 50, (height / 10f), (height / 10f), false, "Joueur Suivant") {
            @Override
            public boolean isVisible() {
                return shoot;
            }
        };

        Text whichPlayer = new Text(121, (width / 2f), (height / 5f), true, null) {
            @Override
            public String getText() {
                return currentPlayer==0 ? "Au joueur 1 de tirer" : "Au joueur 2 de tirer";
            }
        };

        Text whichGrid = new Text(123, 5f*(width / 6f) - (9 * btnSize2 / 2) - 4, 4f*(height / 6f), false, null) {
            @Override
            public String getText() {
                return currentPlayer==0 ? "Tirs du joueur 2" : "Tirs du joueur 1";
            }
        };

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");
        addGuiElement(this.changePlayerButton, backButton, whichPlayer, whichGrid);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == this.changePlayerButton.getId()) {
            this.currentPlayer = this.currentPlayer == 1 ? 0 : 1;
            this.shoot = false;
            return true;
        } else if (id < 100) {
            if (!this.shoot) {
                int x = id / 10;
                int y = id % 10;
                this.battleship.shoot(this.currentPlayer, x, y);
                this.shoot = true;
                if (this.battleship.checkWinCondition(this.currentPlayer)) {
                    this.stopwatch.stop();
                    long time = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
                    stateManager.pushState(new EndGameScreen(stateManager, Constants.GameType.BATTLESHIP, time, calculatePoints()));
                }
            }
        }
        return false;
    }

    private int calculatePoints() {
        long time = this.stopwatch.elapsed(TimeUnit.SECONDS);

        int failedCount = 0;
        for (int i = 0; i < Battleship.GRID_SIZE; i++) {
            for (int j = 0; j < Battleship.GRID_SIZE; j++) {
                int value = this.battleship.getPlayerGrid(this.currentPlayer)[i][j];
                if (value == Battleship.FAILED_HIT) {
                    failedCount++;
                }
            }
        }
        time = time - 120;
        int minusPoint = (int) (((time / 30) * 3) + (failedCount * 10));
        return MathHelper.clamp(600 - minusPoint, 200, 600);
    }
}