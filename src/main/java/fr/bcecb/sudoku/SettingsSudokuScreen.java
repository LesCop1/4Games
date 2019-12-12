package fr.bcecb.sudoku;

import fr.bcecb.input.MouseButton;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.RulesPopUpScreen;
import fr.bcecb.state.gui.ScreenState;

public class SettingsSudokuScreen extends ScreenState {

    private Button sudokuEasyButton;
    private Button sudokuNormalButton;
    private Button sudokuHardButton;
    private Button rulesButton;

    public SettingsSudokuScreen(StateManager stateManager) {
        super(stateManager, "settings_sudoku");
    }

    @Override
    public void initGui() {
        this.sudokuEasyButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Easy");
        this.sudokuNormalButton = new Button(11, (width / 4f) * 2, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Normal");
        this.sudokuHardButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Hard");

        this.rulesButton = new Button(13, (width / 2f), (height / 2f) + (height / 3f), (width / 5f), (height / 10f), true, "Règles");

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        addGuiElement(sudokuEasyButton, sudokuNormalButton, sudokuHardButton, rulesButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == this.sudokuEasyButton.getId()) {
            selectMode(20);
            return true;
        } else if (id == this.sudokuNormalButton.getId()) {
            selectMode(40);
            return true;
        } else if (id == this.sudokuHardButton.getId()) {
            selectMode(60);
            return true;
        } else if (id == this.rulesButton.getId()) {
            String rules = "Lorsque vous lancez une partie de Sudoku, une grille incomplète vous est donnée. Le but sera donc de remplir cette grille sans avoir deux fois le même chiffre sur une ligne, une colonne ou un carré de la grille. Une fois la grille complété correctement vous gagnerez la partie.";
            stateManager.pushState(new RulesPopUpScreen(stateManager, "Sudoku", rules, 200, 125));
            return true;
        }

        return false;
    }

    private void selectMode(int difficulty) {
        stateManager.pushState(new SudokuScreen(stateManager, difficulty));
    }
}
