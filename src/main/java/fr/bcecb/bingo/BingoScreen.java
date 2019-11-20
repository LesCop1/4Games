package fr.bcecb.bingo;

import com.google.common.base.Stopwatch;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameState;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;

import java.util.concurrent.TimeUnit;

public class BingoScreen extends ScreenState {
    private final Stopwatch stopwatch;

    private Bingo bingo;
    private int ticks = 0;
    private int tickMultiplier;
    private int lastDrop;

    private GuiElement ball;

    public BingoScreen(StateManager stateManager, int nbGrids, int difficulty) {
        super(stateManager, "game_bingo");
        this.bingo = new Bingo(nbGrids);
        this.bingo.init();
        switch (difficulty) {
            case 1 -> this.tickMultiplier = 8;
            case 2 -> this.tickMultiplier = 6;
            case 3 -> this.tickMultiplier = 4;
        }
        this.stopwatch = Stopwatch.createStarted();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.bingo.getPlayer().checkWin()) {
            stopwatch.stop();
            long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            stateManager.pushState(new EndGameState(stateManager, Constants.GameType.BINGO, time, 32));
        }

        if (++this.ticks > tickMultiplier * 60) {
            this.lastDrop = this.bingo.dropball();
            this.ticks = 0;
        }
    }

    @Override
    public void initGui() {
        setBackgroundTexture(Constants.BINGO_BACKGROUND);

        float startX = 2.5f * (width / 20f);
        float startY = (height / 5f);

        float gridW = (width / 3f);
        float gridH = (height / 5f);
        float marginW = (width / 10f);
        float marginH = (height / 20f);

        for (int i = 0; i < this.bingo.getNbGrids(); i++) {
            float offsetX = ((float) Math.floor(i / 3f)) * (gridW + marginW);
            float offsetY = (i % 3) * (gridH + marginH);
            float gridX = startX + offsetX;
            float gridY = startY + offsetY;

            drawGrid(gridX, gridY, gridW, gridH, i);
        }

        this.ball = new Text(2000, startX + gridW, height / 10f, false, "", 5f) {
            @Override
            public String getText() {
                return lastDrop != 0 ? Integer.toString(lastDrop) : "";
            }
        };

        final Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back") {
        };

        addGuiElement(backButton, ball);
    }

    @Override
    public boolean mouseClicked(int id) {
        return false;
    }

    private void drawGrid(float gridX, float gridY, float gridW, float gridH, int numGrid) {
        int id = (100 * numGrid);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++, id++) {
                int caseX = i;
                int caseY = j;
                int caseId = id;
                final Button caseButton = new Button(caseId, (gridX + j * (gridW / 9)), (gridY + i * (gridH / 3)), (gridW / 10), (gridH / 3), false) {
                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        int value = getValue();
                        return value != 0 ? Constants.BINGO_CASE : Constants.BINGO_CASE_CHECKED;
                    }

                    @Override
                    public ResourceHandle<Texture> getHoverTexture() {
                        int value = getValue();
                        return value == 0 ? Constants.BINGO_CASE_CHECKED : value > 0 ? Constants.BINGO_CASE_HOVERED : Constants.BINGO_CASE;
                    }

                    @Override
                    public String getTitle() {
                        int value = getValue();
                        return value > 0 ? String.valueOf(value) : "";
                    }

                    @Override
                    public boolean isDisabled() {
                        return getValue() <= 0;
                    }

                    private int getValue() {
                        return bingo.getPlayer().getGrid(numGrid).getValue(caseX, caseY);
                    }
                };

                addGuiElement(caseButton);
            }
        }
    }
}

