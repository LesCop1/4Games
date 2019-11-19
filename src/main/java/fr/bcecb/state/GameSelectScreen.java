package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.batailleNavale.BattleshipScreen;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.SudokuDifficultyScreen;
import fr.bcecb.util.Resources;

public class GameSelectScreen extends ScreenState {
    private GuiElement sudokuGameButton;
    private GuiElement bingoGameButton;
    private GuiElement bsGameButton;
    private GuiElement pokerGameButton;

    private GuiElement profileButton;

    public GameSelectScreen(StateManager stateManager) {
        super(stateManager, "game_select_menu");
    }

    @Override
    public void initGui() {
        this.sudokuGameButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Sudoku", Resources.DEFAULT_BUTTON_TEXTURE);

        this.bingoGameButton = new Button(11, (width / 4f), (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Bingo", Resources.DEFAULT_BUTTON_TEXTURE);

        this.profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Resources.CURRENT_PROFILE_TEXTURE);

        this.bsGameButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Battle Ship", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new BattleshipScreen(stateManager));
            }
        };

        this.pokerGameButton = new Button(13, (width / 4f) * 3, (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Poker", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
            }
        };

        GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(this.sudokuGameButton, this.bingoGameButton, this.profileButton, this.bsGameButton, this.pokerGameButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        if (id == this.sudokuGameButton.getId()) {
            stateManager.pushState(new SudokuDifficultyScreen(stateManager));
            return true;
        } else if (id == this.bingoGameButton.getId()) {
            //TODO
            return true;
        } else if (id == this.bsGameButton.getId()) {
            stateManager.pushState(new BattleshipScreen(stateManager));
            return true;
        } else if (id == this.pokerGameButton.getId()) {
            //TODO
            return true;
        } else if (id == this.profileButton.getId()) {
            stateManager.pushState(new ProfileScreen(stateManager));
            return true;
        }

        return false;
    }
}
