package fr.bcecb.sudoku;

import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Resources;

public class SudokuDifficultyScreen extends ScreenState {

    private GuiElement sudokuEasyButton;
    private GuiElement sudokuNormalButton;
    private GuiElement sudokuHardButton;

    public SudokuDifficultyScreen(StateManager stateManager) {
        super(stateManager, "game_select_menu");
    }

    @Override
    public void initGui() {
        this.sudokuEasyButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Easy", Resources.DEFAULT_BUTTON_TEXTURE);
        this.sudokuNormalButton = new Button(11, (width / 4f) * 2, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Normal", Resources.DEFAULT_BUTTON_TEXTURE);
        this.sudokuHardButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Hard", Resources.DEFAULT_BUTTON_TEXTURE);

        GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE);

        addGuiElement(sudokuEasyButton, sudokuNormalButton, sudokuHardButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        if (id == this.sudokuEasyButton.getId()) {
            selectMode(20);
            return true;
        } else if (id == this.sudokuNormalButton.getId()) {
            selectMode(40);
            return true;
        } else if (id == this.sudokuHardButton.getId()) {
            selectMode(60);
            return true;
        }

        return false;
    }

    private void selectMode(int difficulty) {
        stateManager.pushState(new SudokuState(stateManager, difficulty));
    }
}
