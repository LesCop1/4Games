package fr.bcecb.sudoku;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameScreen;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SudokuScreen extends ScreenState {

    private final Sudoku sudoku;
    private final int difficulty;
    private final Stopwatch stopwatch;
    private List<Integer[]> conflicts = Lists.newArrayList();

    private int selectedX = -1;
    private int selectedY = -1;

    public SudokuScreen(StateManager stateManager, int difficulty) {
        super(stateManager, "game_sudoku");
        this.sudoku = new Sudoku(difficulty);
        this.difficulty = difficulty;
        stopwatch = Stopwatch.createStarted();
    }

    @Override
    public ResourceHandle<Texture> getBackgroundTexture() {
        return Constants.SUDOKU_BACKGROUND;
    }

    @Override
    public void initGui() {
        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        int id = 1;

        float btnSize = 20f;
        float x = (width / 2f) - (9 * btnSize / 2) - 4;
        for (int i = 0; i < 9; ++i, x += btnSize + (i != 0 && i % 3 == 0 ? 4 : 0)) {
            float y = (height / 2f) - (9 * btnSize / 2) - 4;

            for (int j = 0; j < 9; ++j, ++id, y += btnSize + (j != 0 && j % 3 == 0 ? 4 : 0)) {
                this.addGuiElement(new SudokuButton(id, x, y, this.sudoku, i, j));
            }
        }

        for (int i = 0; i < 9; i++) {
            GuiElement candidateValueButton = new SudokuCandidateButton(++id, (width / 2f) - (9 * btnSize / 2) + (btnSize * i), height - btnSize, i + 1);

            addGuiElement(candidateValueButton);
        }

        addGuiElement(backButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        GuiElement guiElement = getGuiElementById(id);

        if (guiElement instanceof SudokuButton) {
            SudokuButton sudokuButton = (SudokuButton) guiElement;

            sudoku.getGrid()[sudokuButton.getCaseX()][sudokuButton.getCaseY()] = 0;

            selectedX = sudokuButton.getCaseX();
            selectedY = sudokuButton.getCaseY();

            return true;
        } else if (guiElement instanceof SudokuCandidateButton) {
            SudokuCandidateButton sudokuCandidateButton = (SudokuCandidateButton) guiElement;
            sudoku.getGrid()[selectedX][selectedY] = sudokuCandidateButton.getValue();

            selectedX = -1;
            selectedY = -1;

            return true;
        }

        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (sudoku.winCondition()) {
            stopwatch.stop();
            long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            stateManager.pushState(new EndGameScreen(stateManager, Constants.GameType.SUDOKU, time, calculateMoneyEarned()));
        }
    }

    private int calculateMoneyEarned() {
        long time = stopwatch.elapsed(TimeUnit.SECONDS);
        int minusToken = (int) Math.max(0, Math.floor(time - (this.difficulty * 2)));

        return Math.max(5, (this.difficulty * 10) - minusToken);
    }

    private static final class SudokuButton extends Button {
        private final Sudoku sudoku;

        private final int caseX;
        private final int caseY;

        public SudokuButton(int id, float x, float y, Sudoku sudoku, int caseX, int caseY) {
            super(id, x, y, 20, 20, true);
            this.sudoku = sudoku;
            this.caseX = caseX;
            this.caseY = caseY;
        }

        public int getCaseX() {
            return caseX;
        }

        public int getCaseY() {
            return caseY;
        }

        @Override
        public boolean isDisabled() {
            return sudoku.winCondition() || sudoku.getGeneratedGrid()[caseX][caseY] != 0;
        }

        @Override
        public String getTitle() {
            return sudoku.getGrid()[caseX][caseY] != 0 ? String.valueOf(sudoku.getGrid()[caseX][caseY]) : "";
        }

        @Override
        public Vector4f getTitleColor() {
            return sudoku.getGeneratedGrid()[caseX][caseY] == 0 ? Constants.COLOR_BLACK : Constants.COLOR_WHITE;
        }

        @Override
        public ResourceHandle<Texture> getTexture() {
            return Constants.SUDOKU_FREE_CASE;
        }

        @Override
        public ResourceHandle<Texture> getHoverTexture() {
            return null;
        }

        @Override
        public ResourceHandle<Texture> getDisabledTexture() {
            return Constants.SUDOKU_FIXED_CASE;
        }
    }

    private final class SudokuCandidateButton extends Button {
        private final int value;

        public SudokuCandidateButton(int id, float x, float y, int value) {
            super(id, x, y, 20.0f, 20.0f, true, String.valueOf(value));
            this.value = value;
        }

        @Override
        public boolean isDisabled() {
            int[] candidateValues = sudoku.computeCandidates(SudokuScreen.this.selectedX, SudokuScreen.this.selectedY);

            for (int candidateValue : candidateValues) {
                if (candidateValue == this.value) return false;
            }

            return true;
        }

        @Override
        public boolean isVisible() {
            return SudokuScreen.this.selectedX != -1 && SudokuScreen.this.selectedY != -1;
        }

        @Override
        public ResourceHandle<Texture> getTexture() {
            return Constants.SUDOKU_FREE_CASE;
        }

        @Override
        public ResourceHandle<Texture> getHoverTexture() {
            return null;
        }

        @Override
        public ResourceHandle<Texture> getDisabledTexture() {
            return Constants.SUDOKU_FIXED_CASE;
        }

        public int getValue() {
            return value;
        }
    }
}

