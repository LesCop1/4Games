package fr.bcecb.state;

import fr.bcecb.batailleNavale.SettingsBattleshipScreen;
import fr.bcecb.bingo.SettingsBingoScreen;
import fr.bcecb.input.MouseButton;
import fr.bcecb.poker.SettingsPokerScreen;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.SettingsSudokuScreen;
import fr.bcecb.util.Resources;

public class GameSelectScreen extends ScreenState {
    private Button sudokuGameButton;
    private Button bingoGameButton;
    private Button bsGameButton;
    private Button pokerGameButton;

    private Button profileButton;

    public GameSelectScreen(StateManager stateManager) {
        super(stateManager, "game_select_menu");
    }

    @Override
    public boolean hasBackgroundMusic() {
        return true;
    }

    @Override
    public void initGui() {
        this.sudokuGameButton = new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Sudoku");

        this.bingoGameButton = new Button(11, (width / 4f), (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Bingo");

        this.profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return Resources.CURRENT_PROFILE_TEXTURE;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }
        };

        this.bsGameButton = new Button(12, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "Battle Ship");

        this.pokerGameButton = new Button(13, (width / 4f) * 3, (height / 2f) + (height / 10f), (width / 8f), (height / 10f), true, "Poker");

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        addGuiElement(this.sudokuGameButton, this.bingoGameButton, this.profileButton, this.bsGameButton, this.pokerGameButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == this.sudokuGameButton.getId()) {
            stateManager.pushState(new SettingsSudokuScreen(stateManager));
            return true;
        } else if (id == this.bingoGameButton.getId()) {
            stateManager.pushState(new SettingsBingoScreen(stateManager));
            return true;
        } else if (id == this.bsGameButton.getId()) {
            stateManager.pushState(new SettingsBattleshipScreen(stateManager));
            return true;
        } else if (id == this.pokerGameButton.getId()) {
            stateManager.pushState(new SettingsPokerScreen(stateManager));
            return true;
        } else if (id == this.profileButton.getId()) {
            stateManager.pushState(new ProfileScreen(stateManager));
            return true;
        }
        return false;
    }
}
