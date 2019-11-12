package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.batailleNavale.BattleshipScreen;
import fr.bcecb.render.Window;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Constants;

public class GameSelectScreen extends ScreenState {
    protected GameSelectScreen() {
        super("game_select_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(Constants.DEFAULT_BACKGROUND_TEXTURE);

        final GuiElement sudokuGameButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Sudoku", Constants.DEFAULT_BUTTON_TEXTURE);

        final GuiElement bingoGameButton = new Button(11, (width / 4f), (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Bingo", Constants.DEFAULT_BUTTON_TEXTURE);

        final GuiElement profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Game.instance().currentProfile)
                .setClickHandler(e -> Game.instance().getStateEngine().pushState(new ProfileScreen()));

        final GuiElement bsGameButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Battle Ship", Constants.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler(e -> Game.instance().getStateEngine().pushState(new BattleshipScreen()));

        final GuiElement pokerGameButton = new Button(13, (width / 4f) * 3, (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Poker", Constants.DEFAULT_BUTTON_TEXTURE);

        final GuiElement backButton = new Button(14, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Constants.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(sudokuGameButton, bingoGameButton, profileButton, bsGameButton, pokerGameButton, backButton);
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
