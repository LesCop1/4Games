package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.batailleNavale.BattleshipScreen;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.DifficultySelectScreen;
import fr.bcecb.sudoku.SudokuState;

public class GameSelectScreen extends ScreenState {
    protected GameSelectScreen() {
        super("game_select_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<Texture>("textures/mainMenuBG.png") {});

        GuiElement sudokuGameButton = new Button(0, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f),
                true, "Sudoku", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new DifficultySelectScreen()));

        GuiElement bingoGameButton = new Button(1, (width / 4f), (height / 2f) + (height / 10f), (width / 8f), (height / 10f),
                true, "Bingo", new ResourceHandle<Texture>("textures/defaultButton.png") {
        });

        GuiElement profileButton = new CircleButton(2, width / 2.0f, height / 2.0f, (height / 10f), true,
                new ResourceHandle<Texture>("textures/defaultProfile.png") {
                }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new ProfileScreen()));

        GuiElement bsGameButton = new Button(3, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f),
                true, "Battle Ship", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new BattleshipScreen()));

        GuiElement pokerGameButton = new Button(4, (width / 4f) * 3, (height / 2f) + (height / 10f), (width / 8f), (height / 10f),
                true, "Poker", new ResourceHandle<Texture>("textures/defaultButton.png") {
        });

        GuiElement backButton = new Button(5, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f),
                (height / 10f), false, "Back", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(sudokuGameButton);
        addGuiElement(bingoGameButton);
        addGuiElement(pokerGameButton);
        addGuiElement(bsGameButton);
        addGuiElement(profileButton);
        addGuiElement(backButton);
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldUpdateBelow() {
        return false;
    }
}
