package fr.bcecb.sudoku;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class DifficultySelectScreen extends ScreenState {
    public DifficultySelectScreen() {
        super("game_select_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<Texture>("textures/mainMenuBG.png") {
        });
        GuiElement sudokuEasyButton = new Button(0, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f),
                true, "Easy", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new SudokuState(SudokuState.Difficulty.EASY)));
        GuiElement sudokuNormalButton = new Button(1, (width / 4f) * 2, (height / 2f) - (height / 10f), (width / 8f), (height / 10f),
                true, "Normal", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new SudokuState(SudokuState.Difficulty.NORMAL)));
        GuiElement sudokuHardButton = new Button(2, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f),
                true, "Hard", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new SudokuState(SudokuState.Difficulty.HARD)));
        GuiElement backButton = new Button(5, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f),
                (height / 10f), false, "Back", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());
        addGuiElement(sudokuEasyButton);
        addGuiElement(sudokuNormalButton);
        addGuiElement(sudokuHardButton);
        addGuiElement(backButton);
    }

}
