package fr.bcecb.sudoku;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Resources;

public class DifficultySelectScreen extends ScreenState {
    public DifficultySelectScreen() {
        super("game_select_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();

        final GuiElement sudokuEasyButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Easy", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, event) -> Game.instance().getStateEngine().pushState(new SudokuState(SudokuState.Difficulty.EASY)));
        final GuiElement sudokuNormalButton = new Button(11, (width / 4f) * 2, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Normal", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, event) -> Game.instance().getStateEngine().pushState(new SudokuState(SudokuState.Difficulty.NORMAL)));
        final GuiElement sudokuHardButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Hard", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, event) -> Game.instance().getStateEngine().pushState(new SudokuState(SudokuState.Difficulty.HARD)));
        final GuiElement backButton = new Button(13, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, event) -> Game.instance().getStateEngine().popState());

        addGuiElement(sudokuEasyButton, sudokuNormalButton, sudokuHardButton, backButton);
    }
}
