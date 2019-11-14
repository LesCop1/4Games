package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.batailleNavale.BattleshipScreen;
import fr.bcecb.render.Window;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.DifficultySelectScreen;
import fr.bcecb.util.Resources;

public class GameSelectScreen extends ScreenState {
    public GameSelectScreen() {
        super("game_select_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();

        final GuiElement sudokuGameButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Sudoku", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, e) -> Game.instance().getStateEngine().pushState(new DifficultySelectScreen()));

        final GuiElement bingoGameButton = new Button(11, (width / 4f), (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Bingo", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, e) -> Game.instance().getStateEngine().pushState(new SettingsBingoState()));

        final GuiElement profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Game.instance().currentProfile)
                .setClickHandler((id, e) -> Game.instance().getStateEngine().pushState(new ProfileScreen()));

        final GuiElement bsGameButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Battle Ship", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, e) -> Game.instance().getStateEngine().pushState(new BattleshipScreen()));

        final GuiElement pokerGameButton = new Button(13, (width / 4f) * 3, (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Poker", Resources.DEFAULT_BUTTON_TEXTURE);

        final GuiElement backButton = new Button(14, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, e) -> Game.instance().getStateEngine().popState());

        addGuiElement(sudokuGameButton, bingoGameButton, profileButton, bsGameButton, pokerGameButton, backButton);
    }
}
