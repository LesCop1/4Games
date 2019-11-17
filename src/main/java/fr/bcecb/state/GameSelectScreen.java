package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.batailleNavale.BattleshipScreen;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.DifficultySelectScreen;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;

public class GameSelectScreen extends ScreenState {
    public GameSelectScreen() {
        super("game_select_menu");
    }

    @Override
    public void initGui() {
        Button sudokuGameButton = new Button(1, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Sudoku", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new DifficultySelectScreen());
            }
        };

        Button bingoGameButton = new Button(2, (width / 4f), (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Bingo", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
            }
        };

        CircleButton profileButton = new CircleButton(3, width / 2.0f, height / 2.0f, (height / 10f), true, Constants.CURRENT_PROFILE_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new ProfileScreen());
            }
        };

        Button bsGameButton = new Button(4, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Battle Ship", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new BattleshipScreen());
            }
        };

        Button pokerGameButton = new Button(5, (width / 4f) * 3, (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Poker", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
            }
        };

        Button backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(sudokuGameButton, bingoGameButton, profileButton, bsGameButton, pokerGameButton, backButton);
    }
}
