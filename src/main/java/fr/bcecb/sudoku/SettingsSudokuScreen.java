package fr.bcecb.sudoku;

import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class SettingsSudokuScreen extends ScreenState {

    private GuiElement sudokuEasyButton;
    private GuiElement sudokuNormalButton;
    private GuiElement sudokuHardButton;

    public SettingsSudokuScreen(StateManager stateManager) {
        super(stateManager, "settings_sudoku");
    }

    @Override
    public void initGui() {
        this.sudokuEasyButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Easy");
        this.sudokuNormalButton = new Button(11, (width / 4f) * 2, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Normal");
        this.sudokuHardButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Hard");

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

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
        stateManager.pushState(new SudokuScreen(stateManager, difficulty));
    }
}
