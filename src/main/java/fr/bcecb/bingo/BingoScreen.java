package fr.bcecb.bingo;

import com.google.common.base.Stopwatch;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameScreen;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import fr.bcecb.util.MathHelper;

import java.util.concurrent.TimeUnit;

public class BingoScreen extends ScreenState {
    private final Stopwatch stopwatch;

    private final int gridCount;
    private final Bingo bingo;
    private final int tickMultiplier;

    private int ticks;
    private int lastDrop;

    public BingoScreen(StateManager stateManager, int gridCount, int difficulty) {
        super(stateManager, "game_bingo");
        setBackgroundTexture(Constants.BINGO_BACKGROUND);

        this.bingo = new Bingo(gridCount);
        this.gridCount = gridCount;
        this.tickMultiplier = MathHelper.clamp(8 - ((difficulty - 1) * 2), 0, 10);
        this.stopwatch = Stopwatch.createUnstarted();
    }

    @Override
    public void onEnter() {
        super.onEnter();

        this.stopwatch.start();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.bingo.hasRemaining() || this.bingo.getPlayer().checkWin()) {
            stopwatch.stop();
            long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            stateManager.pushState(new EndGameScreen(stateManager, Constants.GameType.BINGO, time, 32));
        }

        if (++this.ticks > tickMultiplier * 60) {
            this.lastDrop = this.bingo.dropBall();
            this.ticks = 0;
        }
    }

    @Override
    public void initGui() {
        float gridW = (width / 3f);
        float gridH = (height / 5f);
        float marginW = (width / 10f);
        float marginH = (height / 20f);

        float startX = 2.5f * (width / 20f);
        float startY = (float) ((height / 5.1f) + ((3 - (Math.floor(gridCount / 2f) + (gridCount % 2))) * (gridH + marginH)) / 2);

        for (int i = 0; i < this.gridCount; i++) {

            float offsetX = (i % 2) * (gridW + marginW);
            float offsetY = (float) (Math.floor(i / 2f) * (gridH + marginH));
            float gridX = startX + offsetX;
            float gridY = startY + offsetY;


            if (gridCount % 2 != 0 && i == gridCount - 1) {
                generateGridButtons((width / 2f) - (gridW / 2), gridY, i, (i * 27));
            } else {
                generateGridButtons(gridX, gridY, i, (i * 27));
            }
        }

        GuiElement ball = new Text(200, width / 2f, height / 10f, true, null, 3f) {
            @Override
            public String getText() {
                return MathHelper.stringifyInteger(lastDrop);
            }
        };

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back") {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return Constants.BINGO_BUTTON;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return Constants.BINGO_BUTTON_HOVER;
            }
        };

        addGuiElement(backButton, ball);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        BingoButton bingoButton = (BingoButton) this.getGuiElementById(id);

        if (bingoButton.getValue() == this.lastDrop) {
            this.bingo.getPlayer().setValue(bingoButton.grid, bingoButton.caseX, bingoButton.caseY, 0);
        }

        return false;
    }

    private void generateGridButtons(float gridX, float gridY, int numGrid, int id) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                final Button caseButton = new BingoButton(++id, gridX, gridY, numGrid, i, j);

                addGuiElement(caseButton);
            }
        }
    }

    private final class BingoButton extends Button {
        private final int grid;
        private final int caseX;
        private final int caseY;

        public BingoButton(int id, float x, float y, int numGrid, int caseX, int caseY) {
            super(id, (x + caseY * 15f), (y + caseX * 15f), 15, 15, false);
            this.grid = numGrid;
            this.caseX = caseX;
            this.caseY = caseY;
        }

        @Override
        public ResourceHandle<Texture> getTexture() {
            return getValue() != 0 ? Constants.BINGO_CASE : Constants.BINGO_CASE_OK;
        }

        @Override
        public ResourceHandle<Texture> getHoverTexture() {
            int value = getValue();
            return value >= 0 ? Constants.BINGO_CASE_OK : Constants.BINGO_CASE;
        }

        @Override
        public ResourceHandle<Texture> getDisabledTexture() {
            int value = getValue();
            return value == -1 ? Constants.BINGO_EMPTY_CASE : null;
        }

        @Override
        public String getTitle() {
            return MathHelper.stringifyInteger(getValue(), MathHelper::isPositive);
        }

        @Override
        public boolean isDisabled() {
            return getValue() <= 0;
        }

        private int getValue() {
            return bingo.getPlayer().getValue(grid, caseX, caseY);
        }
    }
}

